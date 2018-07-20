package com.ztrew.cdh.iot;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;
import com.ztrew.cdh.iot.program.Program;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Driver
{

    private static Logger logger = LoggerFactory.getLogger(Driver.class);

    private static Map<String, List<String>> commands = new TreeMap<String, List<String>>();

    private static void printUsage() {

        System.out.println();
        System.out.println("Valid options are:");
        for (String program : commands.keySet()) {
            System.out.println(String.format("%1$" + 35 + "s", program) + " : " + commands.get(program).get(1));
        }
        System.out.println();

        System.exit(1);
    }

    public static void main(String[] args)
    {
        commands.put("generate-weather-data", Arrays.asList("com.ztrew.cdh.iot.program.WeatherGenerator", "Generate IoT Weather Data."));

        if (args.length < 1) printUsage();

        List<String> command = commands.get(args[0]);

        if (command == null) printUsage();

        try {
            Class clazz = Class.forName(command.get(0));

            Constructor constructor = clazz.getConstructor();

            Program program = (Program) constructor.newInstance();

            JCommander jCommander = JCommander.newBuilder()
                    .addObject(program)
                    .build();
            jCommander.setProgramName(args[0]);
            try {
                jCommander.parse(args);
            } catch (ParameterException e) {
                //logger.error(e.getLocalizedMessage());
                jCommander.usage();
                System.exit(1);
            }

            program.run();

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
