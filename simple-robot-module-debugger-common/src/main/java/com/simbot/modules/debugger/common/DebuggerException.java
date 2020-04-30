package com.simbot.modules.debugger.common;

import com.forte.qqrobot.exception.RobotRuntimeException;

/**
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public class DebuggerException extends RobotRuntimeException {
    public DebuggerException() {
    }

    public DebuggerException(String message, Object... format) {
        super(message, format);
    }

    public DebuggerException(String message) {
        super(message);
    }

    public DebuggerException(String message, Throwable cause, Object... format) {
        super(message, cause, format);
    }

    public DebuggerException(String message, Throwable cause) {
        super(message, cause);
    }

    public DebuggerException(Throwable cause) {
        super(cause);
    }

    public DebuggerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public DebuggerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, Object... format) {
        super(message, cause, enableSuppression, writableStackTrace, format);
    }

    public DebuggerException(int pointless, String message) {
        super(pointless, message);
    }

    public DebuggerException(int pointless, String message, Throwable cause) {
        super(pointless, message, cause);
    }

    public DebuggerException(int pointless, String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(pointless, message, cause, enableSuppression, writableStackTrace);
    }
}
