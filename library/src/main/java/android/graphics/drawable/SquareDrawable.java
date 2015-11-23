package android.graphics.drawable;

import android.annotation.TargetApi;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Outline;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.Region;
import android.os.Build;
import android.support.annotation.NonNull;
import android.util.AttributeSet;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

public class SquareDrawable extends Drawable {

    final Drawable mSubDrawable;

    public SquareDrawable(Drawable subDrawable) {
        mSubDrawable = subDrawable;
    }

    @Override
    public void draw(Canvas canvas) {
        mSubDrawable.draw(canvas);
    }

    @Override
    public void setAlpha(int alpha) {
        mSubDrawable.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {
        mSubDrawable.setColorFilter(colorFilter);
    }

    @Override
    public int getOpacity() {
        return mSubDrawable.getOpacity();
    }

    @Override
    public void setBounds(int left, int top, int right, int bottom) {
        setBounds(new Rect(left, top, right, bottom));
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void setHotspot(float x, float y) {
        mSubDrawable.setHotspot(x, y);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void setHotspotBounds(int left, int top, int right, int bottom) {
        mSubDrawable.setHotspotBounds(left, top, right, bottom);
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void getHotspotBounds(Rect outRect) {
        mSubDrawable.getHotspotBounds(outRect);
    }

    @Override
    public void setBounds(@NonNull Rect bounds) {
        if (bounds.width() != bounds.height()) {
            if (bounds.width() > bounds.height()) {
                int left = bounds.width() / 2 - bounds.height() / 2;
                int right = bounds.width() / 2 + bounds.height() / 2;
                bounds.set(left, bounds.top, right, bounds.bottom);
            } else {
                int top = bounds.height() / 2 - bounds.width() / 2;
                int bottom = bounds.height() / 2 + bounds.width() / 2;
                bounds.set(bounds.left, top, bounds.right, bottom);
            }
        }
        mSubDrawable.setBounds(bounds);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public Rect getDirtyBounds() {
        return mSubDrawable.getDirtyBounds();
    }

    @Override
    public void setChangingConfigurations(int configs) {
        mSubDrawable.setChangingConfigurations(configs);
    }

    @Override
    public int getChangingConfigurations() {
        return mSubDrawable.getChangingConfigurations();
    }

    @SuppressWarnings("deprecation")
    @Override
    public void setDither(boolean dither) {
        mSubDrawable.setDither(dither);
    }

    @Override
    public void setFilterBitmap(boolean filter) {
        mSubDrawable.setFilterBitmap(filter);
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public boolean isFilterBitmap() {
        return mSubDrawable.isFilterBitmap();
    }

    @Override
    public Callback getCallback() {
        return mSubDrawable.getCallback();
    }

    @Override
    public void invalidateSelf() {
        mSubDrawable.invalidateSelf();
    }

    @Override
    public void scheduleSelf(Runnable what, long when) {
        mSubDrawable.scheduleSelf(what, when);
    }

    @Override
    public void unscheduleSelf(Runnable what) {
        mSubDrawable.unscheduleSelf(what);
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public int getLayoutDirection() {
        return mSubDrawable.getLayoutDirection();
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public boolean onLayoutDirectionChanged(int layoutDirection) {
        return mSubDrawable.onLayoutDirectionChanged(layoutDirection);
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    public int getAlpha() {
        return mSubDrawable.getAlpha();
    }

    @Override
    public void setColorFilter(int color, @NonNull PorterDuff.Mode mode) {
        mSubDrawable.setColorFilter(color, mode);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void setTint(int tintColor) {
        mSubDrawable.setTint(tintColor);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void setTintList(ColorStateList tint) {
        mSubDrawable.setTintList(tint);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void setTintMode(@NonNull PorterDuff.Mode tintMode) {
        mSubDrawable.setTintMode(tintMode);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public ColorFilter getColorFilter() {
        return mSubDrawable.getColorFilter();
    }

    @Override
    public void clearColorFilter() {
        mSubDrawable.clearColorFilter();
    }


    @Override
    public boolean isStateful() {
        return mSubDrawable.isStateful();
    }

    @Override
    public boolean setState(int[] stateSet) {
        return mSubDrawable.setState(stateSet);
    }

    @Override
    public int[] getState() {
        return mSubDrawable.getState();
    }

    @Override
    public void jumpToCurrentState() {
        mSubDrawable.jumpToCurrentState();
    }

    @Override
    public Drawable getCurrent() {
        return mSubDrawable.getCurrent();
    }

    @Override
    public boolean setVisible(boolean visible, boolean restart) {
        return mSubDrawable.setVisible(visible, restart);
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    public void setAutoMirrored(boolean mirrored) {
        mSubDrawable.setAutoMirrored(mirrored);
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    public boolean isAutoMirrored() {
        return mSubDrawable.isAutoMirrored();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void applyTheme(Resources.Theme t) {
        mSubDrawable.applyTheme(t);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public boolean canApplyTheme() {
        return mSubDrawable.canApplyTheme();
    }

    @Override
    public Region getTransparentRegion() {
        return mSubDrawable.getTransparentRegion();
    }

    @Override
    protected boolean onStateChange(int[] state) {
        return mSubDrawable.onStateChange(state);
    }

    @Override
    protected boolean onLevelChange(int level) {
        return mSubDrawable.onLevelChange(level);
    }

    @Override
    protected void onBoundsChange(Rect bounds) {
        mSubDrawable.onBoundsChange(bounds);
    }

    @Override
    public int getIntrinsicWidth() {
        return mSubDrawable.getIntrinsicWidth();
    }

    @Override
    public int getIntrinsicHeight() {
        return mSubDrawable.getIntrinsicHeight();
    }

    @Override
    public int getMinimumWidth() {
        return mSubDrawable.getMinimumWidth();
    }

    @Override
    public int getMinimumHeight() {
        return mSubDrawable.getMinimumHeight();
    }

    @Override
    public boolean getPadding(@NonNull Rect padding) {
        return mSubDrawable.getPadding(padding);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void getOutline(@NonNull Outline outline) {
        mSubDrawable.getOutline(outline);
    }

    @Override
    public Drawable mutate() {
        return mSubDrawable.mutate();
    }

    @Override
    public void inflate(Resources r, XmlPullParser parser, AttributeSet attrs) throws XmlPullParserException, IOException {
        mSubDrawable.inflate(r, parser, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void inflate(Resources r, XmlPullParser parser, AttributeSet attrs, Resources.Theme theme) throws XmlPullParserException, IOException {
        mSubDrawable.inflate(r, parser, attrs, theme);
    }

    @Override
    public ConstantState getConstantState() {
        return mSubDrawable.getConstantState();
    }
}
