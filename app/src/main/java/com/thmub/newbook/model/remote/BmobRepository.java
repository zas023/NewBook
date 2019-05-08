package com.thmub.newbook.model.remote;

import com.thmub.newbook.bean.ShelfBookBean;
import com.thmub.newbook.bean.bmob.MyBook;
import com.thmub.newbook.bean.event.SyncEvent;
import com.thmub.newbook.model.local.BookShelfRepository;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.bmob.v3.BmobBatch;
import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BatchResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListListener;

/**
 * Created by Zhouas666 on 2019-05-08
 * Github: https://github.com/zas023
 */
public class BmobRepository {

    private static final String TAG = "BmobRepository";

    private static volatile BmobRepository sInstance;

    private BmobRepository() {
    }

    public static BmobRepository getInstance() {
        if (sInstance == null) {
            synchronized (BmobRepository.class) {
                if (sInstance == null) {
                    sInstance = new BmobRepository();
                }
            }
        }
        return sInstance;
    }

    /**********************批量操作***************************/
    /**
     * 批量上传账单
     *
     * @param list
     */
    public void saveBooks(List<BmobObject> list, final List<ShelfBookBean> listB) {
        new BmobBatch().insertBatch(list).doBatch(new QueryListListener<BatchResult>() {

            @Override
            public void done(List<BatchResult> o, BmobException e) {
                if (e == null) {
                    for (int i = 0, n = o.size(); i < n; i++) {
                        if (o.get(i).isSuccess()) {
                            //上传成功后更新本地账单，否则会重复同步
                            ShelfBookBean bookBean = listB.get(i);
                            bookBean.setRid(o.get(i).getObjectId());
                            BookShelfRepository.getInstance().saveShelfBook(bookBean);
                        }
                    }

                }
            }
        });
    }

    /**
     * 批量更新账单
     *
     * @param list
     */
    public void updateBooks(List<BmobObject> list) {

        new BmobBatch().updateBatch(list).doBatch(new QueryListListener<BatchResult>() {

            @Override
            public void done(List<BatchResult> o, BmobException e) {
                if (e == null) {

                }
            }
        });
    }

    /**
     * 批量更新账单
     *
     * @param list
     */
    public void deleteBooks(List<BmobObject> list) {

        new BmobBatch().deleteBatch(list).doBatch(new QueryListListener<BatchResult>() {

            @Override
            public void done(List<BatchResult> o, BmobException e) {
                if (e == null) {

                }
            }
        });
    }

    /**************************同步账单******************************/
    /**
     * 同步账单
     */
    public void syncBook(String userid) {

        BmobQuery<MyBook> query = new BmobQuery<>();
        query.addWhereEqualTo("userId", userid);
        //返回50条数据，如果不加上这条语句，默认返回10条数据
        query.setLimit(500);
        //执行查询方法
        query.findObjects(new FindListener<MyBook>() {
            @Override
            public void done(List<MyBook> object, BmobException e) {
                if (e == null) {
                    List<ShelfBookBean> bBills = BookShelfRepository.getInstance().getAllShelfBooks();
                    //需要上传的账单
                    List<BmobObject> listUpload = new ArrayList<>();
                    List<ShelfBookBean> listBBillUpdate = new ArrayList<>();
                    //需要更新的账单
                    List<BmobObject> listUpdate = new ArrayList<>();
                    //需要删除的账单
                    List<BmobObject> listDelete = new ArrayList<>();

                    HashMap<String, ShelfBookBean> bMap = new HashMap<>();


                    for (ShelfBookBean bBill : bBills) {
                        if (bBill.getRid() == null) {
                            //服务器端id为空，则表示为上传
                            listUpload.add(new MyBook(bBill,userid));
                            //以便账单成功上传后更新本地数据
                            listBBillUpdate.add(bBill);
                        } else
                            bMap.put(bBill.getRid(), bBill);
                    }

                    HashMap<String, MyBook> cMap = new HashMap<>();
                    //服务器账单==》键值对
                    for (MyBook coBill : object) {
                        cMap.put(coBill.getObjectId(), coBill);
                    }

                    List<ShelfBookBean> listsave = new ArrayList<>();
                    List<ShelfBookBean> listdelete = new ArrayList<>();
                    for (Map.Entry<String, ShelfBookBean> entry : bMap.entrySet()) {
                        String rid = entry.getKey();
                        ShelfBookBean bBill=entry.getValue();
                        if (cMap.containsKey(rid)) {
                            if (bBill.getVersion() < 0) {
                                //需要删除的账单
                                listDelete.add(new MyBook(bBill,userid));
                                listdelete.add(bBill);
                            } else {
                                //服务器端数据过期
                                if (bBill.getVersion()>cMap.get(rid).getVersion()) {
                                    listUpdate.add(new MyBook(bBill,userid));
                                }
                            }
                            cMap.remove(rid);
                        }
                    }
                    //提交服务器数据的批量操作
                    if(!listUpload.isEmpty()) saveBooks(listUpload,listBBillUpdate);
                    if(!listUpdate.isEmpty()) updateBooks(listUpdate);
                    if(!listDelete.isEmpty()) deleteBooks(listDelete);

                    //CoBill==》BBill
                    for (Map.Entry<String, MyBook> entry : cMap.entrySet()) {
                        //需要保存到本地的账单
                        listsave.add(entry.getValue().getShelfBook());
                    }
                    //向本地数据库提交的批量操作s
                    BookShelfRepository.getInstance().saveShelfBooks(listsave);
                    BookShelfRepository.getInstance().deleteShelfBooks(listdelete);
                    // 发送同步成功事件
                    EventBus.getDefault().post(new SyncEvent(100));
                }
                else
                    EventBus.getDefault().post(new SyncEvent(200));
            }
        });
    }

}
