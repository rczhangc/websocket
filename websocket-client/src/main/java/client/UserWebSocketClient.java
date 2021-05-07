package client;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import user.UserOperate;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author barnak
 */
public class UserWebSocketClient extends WebSocketClient {

    private final UserOperate user;

    public UserWebSocketClient(String url, UserOperate userOperate) throws URISyntaxException {
        super(new URI(url));
        this.user = userOperate;
    }

    @Override
    public void onOpen(ServerHandshake serverHandshake) {
        System.out.println("用户【"+ user.getUserName() +"】握手...");
    }

    @Override
    public void onMessage(String s) {
        System.out.println("接收到消息："+s);
    }

    @Override
    public void onClose(int i, String s, boolean b) {
        System.out.println("关闭...");
    }

    @Override
    public void onError(Exception e) {
        System.out.println("异常"+e);
    }
}
