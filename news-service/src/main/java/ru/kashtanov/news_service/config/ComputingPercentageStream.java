package ru.kashtanov.news_service.config;

import lombok.Getter;
import ru.kashtanov.news_service.service.RedisService;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Viktor Кashtanov
 */

@Getter
public class ComputingPercentageStream extends InputStream {
    private final RedisService redisService;
    private final InputStream inputStream;
    private final long totalQtyBytes;
    private long passedQtyBytes = 0;
    private final ConcurrentHashMap<String, Double> map = new ConcurrentHashMap<>();
    private final String fileName;
    private double loadPercentage = 0;


    public ComputingPercentageStream(RedisService redisService, InputStream inputStream, long totalQtyBytes, String fileName) {
        this.redisService = redisService;
        this.inputStream = inputStream;
        this.totalQtyBytes = totalQtyBytes;
        this.fileName = fileName;
    }

    @Override
    public int read() throws IOException {
        // delegate
        return inputStream.read();
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        int bytes = inputStream.read(b, off, len);
        if (bytes != -1) {
            passedQtyBytes += bytes;
            double progress = countProgress(passedQtyBytes, totalQtyBytes);
            addProgressToMap(fileName, progress);
            sendPercentWithInterval(2, progress);
        }
        return bytes;
    }


    public double countProgress(double passedQtyBytes, double totalQtyBytes) {
        double rawPercent = passedQtyBytes / totalQtyBytes * 100;
        return Math.ceil(rawPercent);
    }

    public void addProgressToMap(String fileName, double progress) {
        map.put(fileName, progress);
    }

    public void sendPercentWithInterval(double interval, double percent) {
        if (percent - loadPercentage >= interval || percent >= 100.0) {
            loadPercentage = percent;
            redisService.savePercentageToRedis(fileName, this);
        }
    }


}
