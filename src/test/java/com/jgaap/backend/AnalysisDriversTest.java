package com.jgaap.backend;

import com.jgaap.generics.AnalysisDriver;
import lombok.extern.log4j.Log4j;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

@Log4j
public class AnalysisDriversTest {

    @Test
    public void getAnalysisDrivers() {
        List<AnalysisDriver> analysisDrivers = AnalysisDrivers.getAnalysisDrivers();
        log.debug(Arrays.toString(analysisDrivers.toArray()));
    }

    @Test
    public void getAnalysisDriver() {
    }
}