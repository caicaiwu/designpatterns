package com.walf.test.com.walf.test.multithread.Immutable;

/*
* 由于Persion类声明为final类型，不能被子类继承。
* 其成员被声明成final类型，也不会被修改。
* 并且没有set函数。
 */
public final class Persion {
    private final String name;
    private final String address;

    public Persion(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String toString() {
        return "[ Persion: name = " + name + ", address = " + address + " ]";
    }
}
