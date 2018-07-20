package com.ztrew.cdh.iot.program;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class WeatherGenerator implements Program {

    private enum Resolution {
        HOUR ("Hour"),
        MINUTE ("Minute"),
        SECOND ("Second"),
        MILLIS ("Millis");
        private final String resolution;

        Resolution(String resolution) {
            this.resolution = resolution;
        }
    }

    private static Logger logger = LoggerFactory.getLogger(WeatherGenerator.class);

    @Parameter
    private List<String> command = new ArrayList<String>();

    @Parameter(names = "-debug", description = "Debug mode")
    private boolean debug = false;

    @Parameter(names = "--help", description = "Print out this message.", help = true)
    private boolean help = false;

    @Parameter(names = {"--s3-root-bucket", "-s"}, description = "AWS S3 root bucket for hourly weather data.", required = true)
    private String s3RootBucket;

    @Parameter(names = {"--resolution", "-r"}, description = "The resolution of weather data to generate.", required = false)
    private Resolution resolution = Resolution.HOUR;

    public WeatherGenerator() {}

    public void run() {

        JCommander jCommander = new JCommander(this);
        if (help) {
            jCommander.usage();
            return;
        }

        logger.info("S3 Root Bucket: " + s3RootBucket);
        logger.info("Resolution: " + resolution);


    }

}
