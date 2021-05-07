import config.SocketConfig;
import thread.BlockedThreadPool;
import user.UserOperate;

/**
 * @author barnak
 */
public class WebsocketClientStart {

    public static void main(String[] args) {
        // 配置的websocket链接
        String url = SocketConfig.WEB_SOCKET_URL;
        // 创建多线程连接池
        BlockedThreadPool pool = BlockedThreadPool.createBlockedThreadPool(200, "测试");
        // 创建指定数量的用户链接
        for (int i=0; i<SocketConfig.USER_NUM; i++) {
            pool.submit(new UserOperate(String.valueOf(i), url));
        }
    }
}
