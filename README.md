[![](https://jitpack.io/v/WhiteDog-Apps/FoldableActivity.svg)](https://jitpack.io/#WhiteDog-Apps/FoldableActivity)

# FoldableActivity
Android library that simplifies the logic necessary to detect state changes in foldable devices,

## Setup
#### 1. Add the JitPack repository to your build file if you didn't add before.
```gradle
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```

#### 2. Add the dependency
```gradle
dependencies {
    implementation 'com.github.WhiteDog-Apps:FoldableActivity:1.0.2'
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
    override fun onFoldableFlat(foldFeature: FoldingFeature) {
        // Update UI
    }

    override fun onFoldableHalfOpen(foldFeature: FoldingFeature, foldPosture: FoldPosture) {
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
    override fun onFoldableFlat(foldFeature: FoldingFeature) {
        ConstraintLayout.getSharedValues().fireNewValue(R.id.tabletop_fold_guide, 0)
        ConstraintLayout.getSharedValues().fireNewValue(R.id.book_fold_guide, 0)
    }

    override fun onFoldableHalfOpen(foldFeature: FoldingFeature, foldPosture: FoldPosture) {
        val foldPosition: Int = FlexModeHelper.getFoldPosition(root, foldFeature)

        val posture: FoldPosture = FlexModeHelper.getPosture(foldFeature)

        if(posture == FoldPosture.TABLE_TOP) {
            ConstraintLayout.getSharedValues().fireNewValue(R.id.tabletop_fold_guide, foldPosition)
        }
        else if(posture == FoldPosture.BOOK) {
            ConstraintLayout.getSharedValues().fireNewValue(R.id.book_fold_guide, foldPosition)
        }
    }
}
```
