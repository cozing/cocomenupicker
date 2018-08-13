How to

To get a Git project into your build:

Step 1. Add the JitPack repository to your build file
Add it in your root build.gradle at the end of repositories:

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
  
Step 2. Add the dependency

	dependencies {
		implementation 'com.github.User:Repo:Tag'
	}
  
  
Usage

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


