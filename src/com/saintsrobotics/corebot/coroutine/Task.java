package com.saintsrobotics.corebot.coroutine;

import com.zoominfo.util.yieldreturn.Generator;

import java.util.Iterator;
import java.util.function.BooleanSupplier;

public abstract class Task extends Generator<BooleanSupplier> {
    
    BooleanSupplier waiter = () -> true;
    Iterator<BooleanSupplier> iterator;
}