package com.hhh.moviespider.engine;

import org.springframework.stereotype.Component;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

@Component
public class JavaScriptEngine {

    private ScriptEngine scriptEngine = new ScriptEngineManager().getEngineByName("JavaScript");

    public ScriptEngine getEngine() {
        return scriptEngine;
    }
}
