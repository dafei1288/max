package com.dafei1288.max;

import com.dafei1288.max.config.MaxConfig;

import java.io.IOException;

public class TestConfig {
    public static void main(String[] args) throws IOException {
        System.out.println(MaxConfig.getInstance().getStringComparatorRule());
    }
}
