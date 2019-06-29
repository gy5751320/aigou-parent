package com.liuritian.aigou.util;


import org.csource.fastdfs.*;

public class FastDfsUtil {

    //获取配置文件
    public static String CONF_FILENAME = FastDfsUtil.class.getResource("/fdfs_client.conf").getFile();

    /**
     * 文件上传
     */
    public static String upload(byte[] file, String extName) {
        try {
            //读取配置文件初始化
            ClientGlobal.init(CONF_FILENAME);

            TrackerClient tracker = new TrackerClient();
            TrackerServer trackerServer = tracker.getConnection();
            StorageServer storageServer = null;

            StorageClient storageClient = new StorageClient(trackerServer, storageServer);
            String fileIds[] = storageClient.upload_file(file, extName, null);

            //文件上传成功后,拼接出文件存储路径返回(格式:/组名/带后缀文件名)
            System.out.println("上传成功: " + "/" + fileIds[0] + "/" + fileIds[1]);
            return "/" + fileIds[0] + "/" + fileIds[1];

        } catch (Exception e) {
            System.out.println("老子刘日天------------------");
            System.out.println("老子刘日天------------------"+CONF_FILENAME);
            System.out.println(e);
            e.printStackTrace();

            return null;
        }
    }


    /**
     * 删除文件
     */
    public static void delete(String groupName, String fileName) {
        try {
            ClientGlobal.init(CONF_FILENAME);

            TrackerClient tracker = new TrackerClient();
            TrackerServer trackerServer = tracker.getConnection();
            StorageServer storageServer = null;

            StorageClient storageClient = new StorageClient(trackerServer,
                    storageServer);
            int i = storageClient.delete_file(groupName, fileName);
            System.out.println(i == 0 ? "删除成功" : "删除失败:" + i);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("删除异常," + e.getMessage());
        }
    }


    /**
     * 下载文件
     */
    public static byte[] download(String groupName, String fileName) {
        try {

            ClientGlobal.init(CONF_FILENAME);

            TrackerClient tracker = new TrackerClient();
            TrackerServer trackerServer = tracker.getConnection();
            StorageServer storageServer = null;

            StorageClient storageClient = new StorageClient(trackerServer, storageServer);
            byte[] b = storageClient.download_file(groupName, fileName);
            return b;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}