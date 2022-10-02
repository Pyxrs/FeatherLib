package com.simplycmd.featherlib.util;

/**
 * Not quite as good as Rust but it works.
 */
public class Tuple {
    public static class Bi<A, B> {
        public A a;
        public B b;

        public Bi(A a, B b) {
            this.a = a;
            this.b = b;
        }
    }

    public static class Tri<A, B, C> {
        public A a;
        public B b;
        public C c;

        public Tri(A a, B b, C c) {
            this.a = a;
            this.b = b;
            this.c = c;
        }
    }

    public static class Quad<A, B, C, D> {
        public A a;
        public B b;
        public C c;
        public D d;

        public Quad(A a, B b, C c, D d) {
            this.a = a;
            this.b = b;
            this.c = c;
            this.d = d;
        }
    }

    public static class Unlimited {
        private Object[] objects;

        public Unlimited(Object... objects) {
            this.objects = objects;
        }

        public <T> T get(Class<T> cast, int index) {
            return cast.cast(objects[index]);
        }
        public <T> T v(Class<T> cast, int index) {
            return get(cast, index);
        }

        public Object getRaw(int index) {
            return objects[index];
        }
        public Object vr(int index) {
            return getRaw(index);
        }

        public Object[] getAll() {
            return objects;
        }
    }
}
