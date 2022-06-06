/*
 * MIT License
 *
 * Copyright (c) 2021 Imanity
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package io.fairyproject.util;

import io.fairyproject.log.Log;
import lombok.experimental.UtilityClass;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;

@UtilityClass
public class Stacktrace {

    public void print(Throwable throwable) {
        Log.error("An error occurs! : " + getStacktrace(throwable));
    }

    public String getStacktrace(Throwable throwable) {
        StringWriter stack = new StringWriter();
        throwable.printStackTrace(new PrintWriter(stack));

        return stack.toString();
    }

    public Throwable simplifyStacktrace(Throwable throwable) {
        Throwable t = throwable;
        while (t instanceof InvocationTargetException || t instanceof RuntimeException) {
            if (t.getCause() == null) {
                break;
            }
            t = t.getCause();
        }
        return t;
    }

}
