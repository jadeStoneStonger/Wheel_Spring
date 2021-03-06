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

感觉是不是还不如 Boolean.valueOf();或者new Boolean()来的方便快捷。

那还学个啥子哦...


### 原理分析

来分析一下什么场景下需要这个 PropertyEditor 的机制，而不是简单粗暴的 类似这种的 Boolean.valueOf() 工具方法。

当你用 Boolean.valueOf() 时，说明你是清楚上下文的，比如，你知道传入的是"true/false"的字符串，你知道返回值是 Boolean 。

那么如果你不知道呢


### 反思总结




