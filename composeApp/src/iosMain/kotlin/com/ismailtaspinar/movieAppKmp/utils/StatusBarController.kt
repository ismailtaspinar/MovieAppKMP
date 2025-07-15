package com.ismailtaspinar.movieAppKmp.utils

import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.useContents
import platform.CoreGraphics.CGFloat
import platform.UIKit.NSLayoutConstraint
import platform.UIKit.UIApplication
import platform.UIKit.UIColor
import platform.UIKit.UIStatusBarStyleDefault
import platform.UIKit.UIStatusBarStyleLightContent
import platform.UIKit.UIView
import platform.UIKit.setStatusBarStyle

actual object StatusBarController {
    actual fun setStatusBarColor(colorHex: String) {
        val color = hexToUIColor(colorHex)

        val window = UIApplication.sharedApplication.keyWindow ?: return
        val statusBarTag = 999999L

        window.viewWithTag(statusBarTag)?.removeFromSuperview()

        val statusBarView = UIView().apply {
            tag = statusBarTag
            backgroundColor = color
            translatesAutoresizingMaskIntoConstraints = false
        }

        window.addSubview(statusBarView)

        NSLayoutConstraint.activateConstraints(listOf(
            statusBarView.topAnchor.constraintEqualToAnchor(window.topAnchor),
            statusBarView.leadingAnchor.constraintEqualToAnchor(window.leadingAnchor),
            statusBarView.trailingAnchor.constraintEqualToAnchor(window.trailingAnchor),
            statusBarView.heightAnchor.constraintEqualToConstant(getStatusBarHeight())
        ))

        val isDark = isColorDark(colorHex)
        UIApplication.sharedApplication.setStatusBarStyle(
            if (isDark) UIStatusBarStyleLightContent else UIStatusBarStyleDefault,
            animated = true
        )
    }

    actual fun setNavigationBarColor(colorHex: String) {
        val color = hexToUIColor(colorHex)

        val window = UIApplication.sharedApplication.keyWindow ?: return
        val bottomBarTag = 888888L

        window.viewWithTag(bottomBarTag)?.removeFromSuperview()

        val bottomBarView = UIView().apply {
            tag = bottomBarTag
            backgroundColor = color
            translatesAutoresizingMaskIntoConstraints = false
        }

        window.addSubview(bottomBarView)

        val bottomHeight = getBottomSafeAreaHeight()

        NSLayoutConstraint.activateConstraints(listOf(
            bottomBarView.bottomAnchor.constraintEqualToAnchor(window.bottomAnchor),
            bottomBarView.leadingAnchor.constraintEqualToAnchor(window.leadingAnchor),
            bottomBarView.trailingAnchor.constraintEqualToAnchor(window.trailingAnchor),
            bottomBarView.heightAnchor.constraintEqualToConstant(
                if (bottomHeight > 0) bottomHeight else 0.0
            )
        ))

    }

    actual fun setupSystemBars(topColorHex: String, bottomColorHex: String) {
        setStatusBarColor(topColorHex)
        setNavigationBarColor(bottomColorHex)

        UIApplication.sharedApplication.keyWindow?.backgroundColor = hexToUIColor(topColorHex)
    }

    @OptIn(ExperimentalForeignApi::class)
    private fun getStatusBarHeight(): CGFloat {
        val window = UIApplication.sharedApplication.keyWindow
        return window?.safeAreaInsets?.useContents { top } ?: 20.0
    }

    @OptIn(ExperimentalForeignApi::class)
    private fun getBottomSafeAreaHeight(): CGFloat {
        val window = UIApplication.sharedApplication.keyWindow
        return window?.safeAreaInsets?.useContents { bottom } ?: 0.0
    }

    private fun hexToUIColor(hex: String): UIColor {
        val cleanHex = hex.removePrefix("#")
        val red = cleanHex.substring(0, 2).toLong(16) / 255.0
        val green = cleanHex.substring(2, 4).toLong(16) / 255.0
        val blue = cleanHex.substring(4, 6).toLong(16) / 255.0

        return UIColor(red = red, green = green, blue = blue, alpha = 1.0)
    }

    private fun isColorDark(hex: String): Boolean {
        val cleanHex = hex.removePrefix("#")
        val red = cleanHex.substring(0, 2).toLong(16) / 255.0
        val green = cleanHex.substring(2, 4).toLong(16) / 255.0
        val blue = cleanHex.substring(4, 6).toLong(16) / 255.0

        val luminance = 0.299 * red + 0.587 * green + 0.114 * blue
        return luminance < 0.5
    }
}