package config;

/**
 * @author barnak
 */
public interface SocketConfig {

    /**
     * websocket链接地址
     */
    String WEB_SOCKET_URL = "ws://172.16.16.76:8888/websocket";

    /**
     * 需要的用户数量
     */
    int USER_NUM = 200;
}
