package com.github.vitalz.jrest.json4img.cli;

import com.google.inject.Module;
import io.bootique.BQModuleProvider;

public final class CliApplicationProvider implements BQModuleProvider {

    @Override
    public Module module() {
        return new CliApplication();
    }
}
