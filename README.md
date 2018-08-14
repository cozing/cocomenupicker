# How to

To get a Git project into your build:

Step 1. Add the JitPack repository to your build file
------
Add it in your root build.gradle at the end of repositories:
```java
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```

Step 2. Add the dependency
-------

```
	dependencies {
		implementation 'com.github.cozing:cocomenupicker:v1.0.0'
	}
```
  
Usage
-------

```java
List<T> lists1 = new ArrayList<>();
List<List<T>> lists2 = new ArrayList<>();
MultiPickerView multiPickerView = new MultiPickerView(this);
        multiPickerView.setDoublePicker(lists1, lists2);
        multiPickerView.setOnMultiPickerSelectListener(new MultiPickerView.OnMultiPickerSelectListener() {
            @Override
            public void onSelect(int index1, int index2) {
                
            }
        });
multiPickerView.setOnMultiPickerDismissListener(new MultiPickerView.OnMultiPickerDismissListener() {
            @Override
            public void onDismiss() {
                
            }
        });
multiPickerView.show();
```

注意
----
如果需要显示的数据是自定义的结构体，需要该结构体实现MultiPickerViewEventType接口，并在重写方法getPickerViewTextFieldForShow()中返回需要显示的属性，比如：

```java
public class PickerData implements MultiPickerViewEventType {
    private String name;
    private String id;
    
    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(String id) {
        this.id = id;
    }
    
    @Override
    public String getPickerViewTextFieldForShow() {
        return name;
    }
}
```
此时显示的数据为List<PickerData>子单位的name。


