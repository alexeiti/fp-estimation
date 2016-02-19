package com.ati.fpestimation.data.impl;

import com.ati.fpestimation.domain.AppStackType;
import com.ati.fpestimation.domain.AppType;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class FileAppStackRepositoryTest {

    @Test
    public void checkGetAllAppsForStack() throws IOException {
        FileAppStackRepository fileAppRepo = new FileAppStackRepository();
        AppStackType appStackType = new AppStackType(1,"lName","sName");
        List<AppType> allAppsForStack = fileAppRepo.getAllAppsForStack(appStackType.getId());
        assertThat(allAppsForStack.size()).isEqualTo(1);
    }


}
