# **Dragger2** 

### 一、概念

#### 1、什么是注解（Annotation）

**1.1  注解概念**：Java提供了一种原程序中的元素关联任何信息和任何元数据的途径和方法。

<img src=".\annotation.jpg" style="zoom:50%;" />

​                                                                                      **Annotation架构**

###### 源码

```java
#Annotation.java
package java.lang.annotation;
public interface Annotation {
    boolean equals(Object obj);
    int hashCode();
    String toString();
    
    Class<? extends Annotation> annotationType();
}

```

**ElementType.java**：**自定义注解**时，声明**注解**的类型，即可用于修饰什么，如**Override**注解被定义用于修饰方法。

```java
#ElementType.java
package java.lang.annotation;
public enum ElementType {
    /** 类, 接口 (including 注解类型), or 枚举声明 */
    TYPE,

    /** 字段声明 (includes 枚举常量) */
    FIELD,

    /** 方法声明 */
    METHOD,

    /** 参数声明 */
    PARAMETER,

    /** 构造方法声明 */
    CONSTRUCTOR,

    /** 局部变量声明 */
    LOCAL_VARIABLE,

    /** 注解类型声明 */
    ANNOTATION_TYPE,

    /** 包声明 */
    PACKAGE,

    /**
     * Type parameter declaration
     *
     * @since 1.8
     */
    TYPE_PARAMETER,

    /**
     * Use of a type
     *
     * @since 1.8
     */
    TYPE_USE,

    /**
     * Module declaration.
     *
     * @since 9
     */
    MODULE
}

```

**RetentionPolicy.java**：指定**注解**的策略属性

```java
#RetentionPolicy.java
package java.lang.annotation;
public enum RetentionPolicy {
    /**
     * Annotation信息仅存在于编译器处理期间.
     */
    SOURCE,

    /**
     * 注解将由编译器记录在class文件中，但在运行时不需要在VM中保留。
     */
    CLASS,

    /**
     * 编译器将Annotation存储于class文件中，并且可由JVM读入
     *
     * @see java.lang.reflect.AnnotatedElement
     */
    RUNTIME
}
```

**1.2  元注解**

- **@Target(ElementType.METHOD)**：指明注解修饰的类型为**方法**
- **@Retention(RetentionPolicy.SOURCE)**：指明注解**仅在编译时使用**

- **@interface**：修饰的类为注解，自动继承java.lang.annotation.Annotation接口
- **@Inherited** ：所修饰的Annotation将具有继承性。



**1.3  自定义注解**

[自定义注解实例]: https://www.cnblogs.com/whoislcj/p/5671622.html



**1.4  APT注解处理器**

暂无

[【Android】APT]: https://www.jianshu.com/p/7af58e8e3e18



###### 学习链接参考：

[学习参考]: https://www.runoob.com/w3cnote/java-annotation.html



#### 2、Dragger2

**2.1  依赖注入 & Dragger2**

​        Dragger2是依赖注入框架，Butterknife也是一个依赖注入框架。

​        常见注解：

- @Module：修饰class，用于对外提供依赖对象；

- @Provides：修饰方法，被修饰的方法会对应生成创建依赖对象的工厂类；

- @Component：修饰接口，@Component(modules = [XxxxModule::class])，指定对应的XxxxModule提供依赖；

- @SubComponent：

  [网上案例]: https://blog.csdn.net/soslinken/article/details/70231089

- @Named：用于区分同类产生的不同对象；

  [网上案例]: https://www.jianshu.com/p/1d84ba23f4d2

  ![](.\name_u1.png)

  ![](.\name_u2.png)

- @Qulifier：用于修饰 **注解** 类型，主要实现的功能与@Name，用户可以自定义注解使用Qulifier修饰，其功能等同于@Name；

- @Singleton：单例模式；

- @Scope：作用域范围；

  

**2.2  使用方式**

**2.2.1 build.gradle配置**

```java
// Dragger
implementation 'com.google.dagger:dagger:2.40.1'
annotationProcessor 'com.google.dagger:dagger-compiler:2.35'
```

​         编译失败遇到的问题：原因是annotationProcessor 'com.google.dagger:dagger-compiler:2.35'中compiler版本写错了导致的。

```java
Unable to load class 'javax.annotation.Generated'.

This is an unexpected error. Please file a bug containing the idea.log file.
```



**2.2.2 案例**

[LoginDemo]: https://github.com/Zxuehong/LoginDemo

- 定义**LoginModule类**，使用@Module修饰，用户提供依赖。

  **@Provides**修饰地方法用于提供依赖，对于生成如下文件

  - ***LoginModule_ProvideContextFactory.java***  --- 提供context对象
  - ***LoginModule_ProvideLoginViewFactory.java***  --- 提供UserInfoView对象
  - ***LoginModule_ProvideLoginButtonFactory.java***  --- 提供LoginButton对象

```kotlin
# LoginModule.kt
package com.example.login.dragger2.module

import android.content.Context
import com.example.login.MainActivity
import com.example.login.R
import com.example.login.view.LoginButton
import com.example.login.view.UserInfoView
import dagger.Module
import dagger.Provides

@Module
class LoginModule(private val activity: MainActivity) {

    @Provides
    fun provideContext(): Context {
        return activity.applicationContext
    }

    @Provides
    fun provideLoginView(): UserInfoView {
        return activity.findViewById(R.id.login_view)
    }

    @Provides
    fun provideLoginButton(): LoginButton {
        return activity.findViewById(R.id.confirm)
    }
}
```

- 定义接口**LoginComponent**，作为依赖注入的桥梁。APT解析生成：***DaggerLoginComponent.java***

​        指定modules = [LoginModule::class]，用于从LoginModule中获取依赖。

```kotlin
# LoginComponent.kt
package com.example.login.dragger2.compoment

import com.example.login.MainActivity
import com.example.login.dragger2.module.LoginModule
import dagger.Component

@Component(modules = [LoginModule::class])
interface LoginComponent {
    fun inject(activity: MainActivity)
}
```

- 使用Dragger2注入依赖

  ​        在MainActivity中使用DaggerLoginComponent进行依赖注入，对于生成***MainActivity_MembersInjector.java***文件。使用@Inject修饰地对象或构造方法，会对应生成如下文件：

  - ***LoginManager_Factory.java***

  - ***UserInfoViewController_Factory.java***

  - ***LoginButtonController_Factory.java***

  ```java
  package com.example.login;
  
  import androidx.appcompat.app.AppCompatActivity;
  
  import android.os.Bundle;
  
  import com.example.login.controller.LoginManager;
  import com.example.login.dragger2.compoment.DaggerLoginComponent;
  import com.example.login.dragger2.module.LoginModule;
  
  import javax.inject.Inject;
  
  public class MainActivity extends AppCompatActivity {
  
      @Inject
      LoginManager mLoginManager;
  
      @Override
      protected void onCreate(Bundle savedInstanceState) {
          ...
          initDragger();
          ...
      }
  
      private void initDragger() {
          DaggerLoginComponent.builder()
                  .loginModule(new LoginModule(this))
                  .build()
                  .inject(this);
      }
      ...
  }
  ```

  

**2.3  原理解析**

- **代码文件生成**

  <img src=".\files_generate.png" style="zoom:50%;" />

- **代码解析**

  ​       当我们创建 **接口** LoginComponent后，并使用**@Component**修饰，APT会自动生成DaggerLoginComponent.java文件。DaggerLoginComponent采用的创建者模式。LoginDemo中LoginManager对象使用@Inject修饰，其初始化时序图如下：

   <img src=".\xxx.png" style="zoom:50%;" />

  ​        在injectMainActivity代码中调用MainActivity_MembersInjector的injectMLoginManager方法对mLoginManager进行初始化。

  ![](.\LoginManager_init.png)

  ![](.\LoginManager_init2.png)

  

  ​        **LoginManager**对象通过loginManager()，其所需的依赖通过LoginModule_ProvideXxxxFactory类获取。

  ```java
  # DaggerLoginComponent.java
  public final class DaggerLoginComponent implements LoginComponent {
      ...
      private LoginManager loginManager() {
          return new LoginManager(
              LoginModule_ProvideContextFactory.provideContext(loginModule),
              userInfoViewController(),
              loginButtonController());
      }
  
      private UserInfoViewController userInfoViewController() {
          return new UserInfoViewController(
              LoginModule_ProvideLoginViewFactory.provideLoginView(loginModule));
      }
  
      private LoginButtonController loginButtonController() {
          return new LoginButtonController(
              LoginModule_ProvideLoginButtonFactory.provideLoginButton(loginModule));
      }
      ...
  }
  ```

- **Dragger注解 - APT解析**

  

#### 3、SystemUI的Dragger使用

