package user;

import org.java_websocket.WebSocket;
import client.UserWebSocketClient;

import java.net.URISyntaxException;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 用户行为
 *
 * @author barnak
 */
public class UserOperate implements Runnable {

    /**
     * 用户编号
     */
    private final String userId;

    /**
     * 用户名称
     */
    private final String userName;

    /**
     * websocket 客户端服务
     */
    private final UserWebSocketClient client;

    public UserOperate(String userId, String webSocketUrl) {
        if (null == userId) {
            throw new RuntimeException("userId not be null");
        }
        if (null == webSocketUrl) {
            throw new RuntimeException("webSocketUrl not be null");
        }
        this.userId = userId;
        this.userName = "用户"+userId;
        try {
            this.client = new UserWebSocketClient(webSocketUrl, this);
        } catch (URISyntaxException e) {
            throw new RuntimeException("用户【"+ userName +"】创建链接失败", e);
        }
    }

    @Override
    public void run() {
        client.connect();
        AtomicInteger countAtomic = new AtomicInteger(0);
        while (!client.getReadyState().equals(WebSocket.READYSTATE.OPEN)) {
            countAtomic.incrementAndGet();
        }
        System.out.println("用户【"+ userName +"】尝试连接"+ countAtomic.get() +"次后成功");
        client.send("我是"+ userName +"，我来啦！我的用户ID是"+ userId);
    }

    public String getUserName() {
        return userName;
    }
}
