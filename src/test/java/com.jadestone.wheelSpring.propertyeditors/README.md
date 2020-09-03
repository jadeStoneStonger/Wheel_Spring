## propertyeditors 用法及原理分析

spring的基础是 **ApplicationContext**, **ApplicationContext** 的基础是 **BeanFactory**.
**BeanFactory** 的基础是 **BeanWrapper**，而BeanWrapper的基础就是**PropertyEditors**了。
所以在spring的学习中，PropertyEditors 是我们第一个应该学习的。

> 真正读懂了一个接口的表现是:
>>自己用一句话就能概括出该接口的作用

### 概述

PropertyEditors 用于处理 字符串 和 特定类型实例 之间转换，转换规则可由自己随便定义。
例如：
-   输入 "aa", 输出 一个 AA 类的实例；
-   或者输入 "aa", 输出一个 File 实例，内容为 aa；
-   亦或者输入 "aa", 输出一个 11。
等等。。反之亦然。

### 用法举例
要注意，PropertyEditor 是Java bean包下的类，并不是Spring新推出来的概念。

我们主要关注 PropertyEditor 的四个方法，别的方法我们不需要关心，分别为：
-   void setValue(Object value)
-   Object getValue()
-   void setAsText(String text)
-   String getAsText()

用法分为两种，就是
-   先setAsText，再getValue 
-   先setValue，再getAsText

有种【反序列化】和【序列化】的感觉

Spring自带着提供了好几种XXXEditor, 下面以 CustomBooleanEditor 举例看一下：

ClassEditor源码如下：
```
public class CustomBooleanEditor extends PropertyEditorSupport {

	private boolean allowEmpty;

	/**
	 * Create a new instance.
	 * <p>The allowEmpty parameter states if an empty String should
	 * be allowed for parsing, i.e. get interpreted as null value.
	 * Else, an IllegalArgumentException gets thrown in that case.
	 * @param allowEmpty if empty strings should be allowed
	 */
	public CustomBooleanEditor(boolean allowEmpty) {
		this.allowEmpty = allowEmpty;
	}

	public void setAsText(String text) throws IllegalArgumentException {
		if (this.allowEmpty && text.trim().equals("")) {
			setValue(null);
		}
		else if (text.equalsIgnoreCase("true")) {
			setValue(Boolean.TRUE);
		}
		else if (text.equalsIgnoreCase("false")) {
			setValue(Boolean.FALSE);
		}
		else
			throw new IllegalArgumentException("Invalid Boolean value [" + text + "]");
	}

}
```

CustomBooleanEditor 用法如下：
```
CustomBooleanEditor customBooleanEditor = new CustomBooleanEditor();
// 传入一个字段串
customBooleanEditor.setAsTest("true");
返回一个 Boolean 对象 Boolean.TRUE
customBooleanEditor.getValue();

```

### 应用场景
Spring中的应用场景就是数据绑定，比如web form表单映射成java bean，或者spring xml配置信息映射成BeanDefinition等。

第一步是：

首先会初始化 PropertyEditors 处理器，
```
	private static final Map defaultEditors = new HashMap();

	static {
		defaultEditors.put(Class.class, ClassEditor.class);
		defaultEditors.put(File.class, FileEditor.class);
		defaultEditors.put(Locale.class, LocaleEditor.class);
		defaultEditors.put(Properties.class, PropertiesEditor.class);
		defaultEditors.put(String[].class, StringArrayPropertyEditor.class);
		defaultEditors.put(URL.class, URLEditor.class);
	}

```
这些都是默认的属性编辑器，一般还会有自定义属性编辑器，这时就会有：
```
	private Map customEditors;
	
	private void doRegisterCustomEditor(Class requiredType, PropertyEditor propertyEditor) {
    		if (this.customEditors == null) {
    			this.customEditors = new HashMap();
    		}
    		this.customEditors.put(requiredType, propertyEditor);
    }
    
```

第二步是 读取java bean的属性信息， 这个也很简单，jdk已经帮我们实现好了，
```
    private BeanInfo beanInfo;
    
	private CachedIntrospectionResults(Class clazz) throws FatalBeanException {
        // jdk自带工具类，获取bean信息
        this.beanInfo = Introspector.getBeanInfo(clazz);
        // 将成员变量的属性信息保存下来， 属性名/属性具体信息
        this.propertyDescriptorMap = new HashMap();
        
        PropertyDescriptor[] pds = this.beanInfo.getPropertyDescriptors();
        for (int i = 0; i < pds.length; i++) {
            this.propertyDescriptorMap.put(pds[i].getName(), pds[i]);
        }
    }
```

第三步 前端或者数据库传过来一串String，额，讲的有点深了，这属于




### 原理分

接口 PropertyEditor 抽象了 String数据格式和 具体对象格式 之间 相互转化的逻辑。


### 反思总结




