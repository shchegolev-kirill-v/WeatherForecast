package org.kshchegolev.weatherforecast.presentation.views.dsl

import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.Space
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.google.android.material.textview.MaterialTextView

inline fun ViewGroup.verticalLayout(init: LinearLayout.() -> Unit): LinearLayout {
    return LinearLayout(context).apply {
        orientation = LinearLayout.VERTICAL
        this@verticalLayout.addView(this)
        init()
    }
}

inline fun ViewGroup.horizontalLayout(init: LinearLayout.() -> Unit): LinearLayout {
    return LinearLayout(context).apply {
        orientation = LinearLayout.HORIZONTAL
        this@horizontalLayout.addView(this)
        init()
    }
}

inline fun ViewGroup.frameLayout(init: FrameLayout.() -> Unit): FrameLayout {
    return FrameLayout(context).apply {
        this@frameLayout.addView(this)
        init()
    }
}

inline fun ViewGroup.cardView(init: MaterialCardView.() -> Unit): MaterialCardView {
    return MaterialCardView(context).apply {
        this@cardView.addView(this)
        init()
    }
}

inline fun ViewGroup.imageView(init: ImageView.() -> Unit): ImageView {
    return ImageView(context).apply {
        this@imageView.addView(this)
        init()
    }
}

inline fun ViewGroup.textView(
    text: String = "",
    init: MaterialTextView.() -> Unit = {}
): MaterialTextView {
    return MaterialTextView(context).apply {
        this.text = text
        this@textView.addView(this)
        init()
    }
}

inline fun ViewGroup.button(
    text: String = "",
    init: MaterialButton.() -> Unit = {}
): MaterialButton {
    return MaterialButton(context).apply {
        this.text = text
        this@button.addView(this)
        init()
    }
}

inline fun ViewGroup.progress(
    init: CircularProgressIndicator.() -> Unit = {}
): CircularProgressIndicator {
    return CircularProgressIndicator(context).apply {
        this@progress.addView(this)
        init()
    }
}

inline fun ViewGroup.recyclerView(init: RecyclerView.() -> Unit): RecyclerView {
    return RecyclerView(context).apply {
        init()
        this@recyclerView.addView(this)
    }
}

inline fun ViewGroup.appBarLayout(
    init: AppBarLayout.() -> Unit = {}
): AppBarLayout {
    return AppBarLayout(context).apply {
        this@appBarLayout.addView(this)
        init()
    }
}
inline fun ViewGroup.toolbar(
    init: MaterialToolbar.() -> Unit = {}
): MaterialToolbar {
    return MaterialToolbar(context).apply {
        this@toolbar.addView(this)
        init()
    }
}

inline fun ViewGroup.swipeRefreshLayout(
    init: SwipeRefreshLayout.() -> Unit = {}
): SwipeRefreshLayout {
    return SwipeRefreshLayout(context).apply {
        this@swipeRefreshLayout.addView(this)
        init()
    }
}

inline fun ViewGroup.scrollView(
    init: ScrollView.() -> Unit = {}
): ScrollView {
    return ScrollView(context).apply {
        this@scrollView.addView(this)
        init()
    }
}

inline fun ViewGroup.space(
    init: Space.() -> Unit = {}
): Space {
    return Space(context).apply {
        this@space.addView(this)
        init()
    }
}

inline fun ViewGroup.constraintLayout(init: ConstraintLayout.() -> Unit): ConstraintLayout {
    return ConstraintLayout(context).apply {
        this@constraintLayout.addView(this)
        init()
    }
}

inline fun AppCompatActivity.setContent(init: LinearLayout.() -> Unit) {
    val root = LinearLayout(this).apply {
        orientation = LinearLayout.VERTICAL
    }
    setContentView(root)
    ViewCompat.setOnApplyWindowInsetsListener(root) { v, insets ->
        val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
        v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
        insets
    }
    root.init()
}

