package org.example.sse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 服务器端实时推送技术之 SseEmitter 的用法测试
 * 测试步骤:
 * 1.请求http://localhost:8888/sse/start?clientId=111接口,浏览器会阻塞,等待服务器返回结果;
 * 2.请求http://localhost:8888/sse/send?clientId=111接口,可以请求多次,并观察第1步的浏览器返回结果;
 * 3.请求http://localhost:8888/sse/end?clientId=111接口结束某个请求,第1步的浏览器将结束阻塞;
 * 其中clientId代表请求的唯一标志;
 *
 * @author syj
 */
@RestController
@RequestMapping("/sse")
public class SseEmitterController {
    private static final Logger logger = LoggerFactory.getLogger(SseEmitterController.class);

    // 用于保存每个请求对应的 SseEmitter
    private Map<String, Result> sseEmitterMap = new ConcurrentHashMap<>();

//    @GetMapping(path = {"/subscribe"}, produces = {"text/event-stream;charset=UTF-8"})
    // for test
    @GetMapping("/subscribe")
    public ResponseEntity<SseEmitter> subscribe(String clientId) {
        // 默认30秒超时,设置为0L则永不超时
//        SseEmitter sseEmitter = new SseEmitter(3600_000L);
        final var emitter = new SseEmitter(0L);
        var result = new Result();
        result.setSseEmitter(emitter);
        result.setTimestamp(System.currentTimeMillis());
        result.setClientId(clientId);
        sseEmitterMap.put(clientId, result);
        ExecutorService service = Executors.newSingleThreadExecutor();
        service.execute(() -> {
            for (;;) {
                try {
                    logger.info("sse send: " + System.currentTimeMillis());
                    emitter.send(String.valueOf(System.currentTimeMillis()));
//                    emitter.send(SseEmitter.event().name("complete").data(String.valueOf(System.currentTimeMillis())));
                    Thread.sleep(3000);
                } catch (Exception e) {
                    logger.info("sse stop");
                    emitter.completeWithError(e);
                    break;
                }
            }
        });
        return ResponseEntity.ok()
                .cacheControl(CacheControl.noCache())
                .body(emitter);
    }

    /**
     * 返回SseEmitter对象
     *
     * @param clientId
     * @return
     */
    @RequestMapping("/start")
    public SseEmitter testSseEmitter(String clientId) {
        // 默认30秒超时,设置为0L则永不超时
        SseEmitter sseEmitter = new SseEmitter(0L);
        var result = new Result();
        result.setSseEmitter(sseEmitter);
        result.setClientId(clientId);
        result.setTimestamp(System.currentTimeMillis());
        return sseEmitter;
    }

    /**
     * 向SseEmitter对象发送数据
     *
     * @param clientId
     * @return
     */
    @RequestMapping("/send")
    public String setSseEmitter(String clientId) {
        try {
            Result result = sseEmitterMap.get(clientId);
            if (result != null && result.sseEmitter != null) {

                result.sseEmitter.send("sbsb");
            }
        } catch (IOException e) {
            logger.error("IOException!", e);
            return "error";
        }
        return "Succeed!";
    }

    /**
     * 将SseEmitter对象设置成完成
     *
     * @param clientId
     * @return
     */
    @RequestMapping("/end")
    public String completeSseEmitter(String clientId) {
        Result result = sseEmitterMap.get(clientId);
        if (result != null) {
            sseEmitterMap.remove(clientId);
            result.sseEmitter.complete();
        }
        return "Succeed!";
    }

    private class Result {
        public String clientId;
        public long timestamp;
        public SseEmitter sseEmitter;
        public String serverId;

        public Result() {
        }

        public String getClientId() {
            return clientId;
        }

        public void setClientId(String clientId) {
            this.clientId = clientId;
        }

        public long getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(long timestamp) {
            this.timestamp = timestamp;
        }

        public SseEmitter getSseEmitter() {
            return sseEmitter;
        }

        public void setSseEmitter(SseEmitter sseEmitter) {
            this.sseEmitter = sseEmitter;
        }
    }
}