package com.q1angch0u.demo.classloader;

import java.io.IOException;
import java.io.InputStream;

public class ClassLoaderTest {

    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        ClassLoader myClassLoader = new ClassLoader() {
            @Override
            public Class<?> loadClass(String name) throws ClassNotFoundException {
                String fileName = name.substring(name.lastIndexOf(".") + 1) + ".class";
                try (InputStream is = getClass().getResourceAsStream(fileName)) {
                    if (is == null) {
                        return super.loadClass(name);
                    }
                    byte[] rawData = new byte[is.available()];
                    is.read(rawData);
                    return defineClass(name, rawData, 0, rawData.length);
                } catch (IOException e) {
                    throw new ClassNotFoundException();
                }
            }
        };

        Object obj1 = ClassLoaderTest.class.getClassLoader().loadClass("com.q1angch0u.demo.classloader.ClassLoaderTest").newInstance();
        System.out.println(obj1.getClass());
        System.out.println(obj1 instanceof com.q1angch0u.demo.classloader.ClassLoaderTest);

        Object obj2 = myClassLoader.loadClass("com.q1angch0u.demo.classloader.ClassLoaderTest").newInstance();
        System.out.println(obj2.getClass());
        System.out.println(obj2 instanceof com.q1angch0u.demo.classloader.ClassLoaderTest);

    }

}
