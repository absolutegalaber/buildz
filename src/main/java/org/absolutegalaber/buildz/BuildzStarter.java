package org.absolutegalaber.buildz;

import org.springframework.boot.SpringApplication;

/**
 * Created by Josip.Mihelko @ Gmail
 */
public class BuildzStarter {
    public static void main(String[] args) {
        SpringApplication.run(new Class[]{BuildzServer.class}, args);
    }
}
