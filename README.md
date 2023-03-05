[![](https://jitpack.io/v/WhiteDog-Apps/FoldableActivity_Android.svg)](https://jitpack.io/#WhiteDog-Apps/FoldableActivity_Android)

# FoldableActivity
Android library that simplifies the logic necessary to detect state changes in foldable devices,

## Setup
#### 1. Add the JitPack repository to your "settings.gradle" file if you didn't add before.
```gradle
pluginManagement {
...
}
dependencyResolutionManagement {
    ...
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
...
```

#### 2. Add the dependency
```gradle
dependencies {
    implementation 'com.github.WhiteDog-Apps:FoldableActivity_Android:1.0.4'
}
```

## Usage
#### 1.  Make your activity inherit from FoldableActivity
```kotlin
class MainActivity: FoldableActivity() {
    ...
}
```

#### 2.  Implements members
```kotlin
    override fun getRootView(): View {
        // Return the view where you will control the fold
    }

    override fun onFoldablePostureChanged(foldPosture: FoldPosture, foldPositionFromEnd: Int) {
        // Update UI
    }
```

## Example
```kotlin
    // 1. Make your activity inherit from FoldableActivity
class MainActivity: FoldableActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    // 2. Implements members
    override fun getRootView(): View {
        return findViewById<ConstraintLayout>(R.id.motion_layout_guides_root)
    }
    
    override fun onFoldablePostureChanged(foldPosture: FoldPosture, foldPositionFromEnd: Int) {
        when(foldPosture) {
            FoldPosture.TABLE_TOP -> {
                ConstraintLayout.getSharedValues().fireNewValue(R.id.rg_motion_layout_guides_tabletop, foldPositionFromEnd)
                ConstraintLayout.getSharedValues().fireNewValue(R.id.rg_motion_layout_guides_book, 0)
            }
            FoldPosture.BOOK      -> {
                ConstraintLayout.getSharedValues().fireNewValue(R.id.rg_motion_layout_guides_tabletop, 0)
                ConstraintLayout.getSharedValues().fireNewValue(R.id.rg_motion_layout_guides_book, foldPositionFromEnd)
            }
            else                  -> {
                ConstraintLayout.getSharedValues().fireNewValue(R.id.rg_motion_layout_guides_tabletop, 0)
                ConstraintLayout.getSharedValues().fireNewValue(R.id.rg_motion_layout_guides_book, 0)
            }
        }
    }
}
```
