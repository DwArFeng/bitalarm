package com.dwarfeng.bitalarm.sdk.handler.pusher;

import com.dwarfeng.bitalarm.sdk.handler.Pusher;

import java.util.Objects;

/**
 * 抽象推送器。
 *
 * <p>
 * 推送器的抽象实现。
 *
 * @author DwArFeng
 * @since 1.8.0
 */
public abstract class AbstractPusher implements Pusher {

    protected String pusherType;

    public AbstractPusher() {
    }

    public AbstractPusher(String pusherType) {
        this.pusherType = pusherType;
    }

    @Override
    public boolean supportType(String type) {
        return Objects.equals(pusherType, type);
    }

    public String getPusherType() {
        return pusherType;
    }

    public void setPusherType(String pusherType) {
        this.pusherType = pusherType;
    }

    @Override
    public String toString() {
        return "AbstractPusher{" +
                "pusherType='" + pusherType + '\'' +
                '}';
    }
}
