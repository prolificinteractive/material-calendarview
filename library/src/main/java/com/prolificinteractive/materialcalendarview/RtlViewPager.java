package com.prolificinteractive.materialcalendarview;

import android.content.Context;
import android.database.DataSetObserver;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.util.HashMap;

public class RtlViewPager extends ViewPager {

    private final HashMap<OnPageChangeListener, ReversingOnPageChangeListener> mPageChangeListeners = new HashMap<>();
    private int mLayoutDirection = ViewCompat.LAYOUT_DIRECTION_LTR;

    public RtlViewPager(Context context) {
        super(context);
    }

    public RtlViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void onRtlPropertiesChanged(int layoutDirection) {
        super.onRtlPropertiesChanged(layoutDirection);
        int viewCompatLayoutDirection = layoutDirection == View.LAYOUT_DIRECTION_RTL ? ViewCompat.LAYOUT_DIRECTION_RTL : ViewCompat.LAYOUT_DIRECTION_LTR;
        if (viewCompatLayoutDirection != mLayoutDirection) {
            PagerAdapter adapter = super.getAdapter();
            int position = 0;
            if (adapter != null) {
                position = getCurrentItem();
            }
            mLayoutDirection = viewCompatLayoutDirection;
            if (adapter != null) {
                adapter.notifyDataSetChanged();
                setCurrentItem(position);
            }
        }
    }

    @Override
    public PagerAdapter getAdapter() {
        ReversingAdapter adapter = (ReversingAdapter) super.getAdapter();
        return adapter == null ? null : adapter.getDelegate();
    }

    @Override
    public void setAdapter(PagerAdapter adapter) {
        if (adapter != null) {
            adapter = new ReversingAdapter(adapter);
        }
        super.setAdapter(adapter);
        setCurrentItem(0);
    }

    private boolean isRtl() {
        return mLayoutDirection == ViewCompat.LAYOUT_DIRECTION_RTL;
    }

    @Override
    public int getCurrentItem() {
        int item = super.getCurrentItem();
        PagerAdapter adapter = super.getAdapter();
        if (adapter != null && isRtl()) {
            item = adapter.getCount() - item - 1;
        }
        return item;
    }

    @Override
    public void setCurrentItem(int position) {
        PagerAdapter adapter = super.getAdapter();
        if (adapter != null && isRtl()) {
            position = adapter.getCount() - position - 1;
        }
        super.setCurrentItem(position);
    }

    @Override
    public void setCurrentItem(int position, boolean smoothScroll) {
        PagerAdapter adapter = super.getAdapter();
        if (adapter != null && isRtl()) {
            position = adapter.getCount() - position - 1;
        }
        super.setCurrentItem(position, smoothScroll);
    }

    @Override
    public Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        return new SavedState(superState, mLayoutDirection);
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
        if (!(state instanceof SavedState)) {
            super.onRestoreInstanceState(state);
            return;
        }

        SavedState ss = (SavedState) state;
        mLayoutDirection = ss.mLayoutDirection;
        super.onRestoreInstanceState(ss.mViewPagerSavedState);
    }

    @Override
    public void addOnPageChangeListener(@NonNull OnPageChangeListener listener) {
        ReversingOnPageChangeListener reversingListener = new ReversingOnPageChangeListener(listener);
        mPageChangeListeners.put(listener, reversingListener);
        super.addOnPageChangeListener(reversingListener);
    }

    @Override
    public void removeOnPageChangeListener(@NonNull OnPageChangeListener listener) {
        ReversingOnPageChangeListener reverseListener = mPageChangeListeners.remove(listener);
        if (reverseListener != null) {
            super.removeOnPageChangeListener(reverseListener);
        }
    }

    @Override
    public void clearOnPageChangeListeners() {
        super.clearOnPageChangeListeners();
        mPageChangeListeners.clear();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.UNSPECIFIED) {
            int height = 0;
            for (int i = 0; i < getChildCount(); i++) {
                View child = getChildAt(i);
                child.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
                int h = child.getMeasuredHeight();
                if (h > height) {
                    height = h;
                }
            }
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public static class SavedState implements Parcelable {

        // The `CREATOR` field is used to create the parcelable from a parcel, even though it is never referenced directly.
        public static final Parcelable.ClassLoaderCreator<SavedState> CREATOR
                = new Parcelable.ClassLoaderCreator<SavedState>() {

            @Override
            public SavedState createFromParcel(Parcel source) {
                return createFromParcel(source, null);
            }

            @Override
            public SavedState createFromParcel(Parcel source, ClassLoader loader) {
                return new SavedState(source, loader);
            }

            @Override
            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
        private final Parcelable mViewPagerSavedState;
        private final int mLayoutDirection;

        private SavedState(Parcelable viewPagerSavedState, int layoutDirection) {
            mViewPagerSavedState = viewPagerSavedState;
            mLayoutDirection = layoutDirection;
        }

        private SavedState(Parcel in, ClassLoader loader) {
            if (loader == null) {
                loader = getClass().getClassLoader();
            }
            mViewPagerSavedState = in.readParcelable(loader);
            mLayoutDirection = in.readInt();
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            out.writeParcelable(mViewPagerSavedState, flags);
            out.writeInt(mLayoutDirection);
        }
    }

    public static class DelegatingPagerAdapter extends PagerAdapter {

        private final PagerAdapter mDelegate;

        DelegatingPagerAdapter(@NonNull final PagerAdapter delegate) {
            this.mDelegate = delegate;
            delegate.registerDataSetObserver(new MyDataSetObserver(this));
        }

        PagerAdapter getDelegate() {
            return mDelegate;
        }

        @Override
        public int getCount() {
            return mDelegate.getCount();
        }

        @Override
        public void startUpdate(@NonNull ViewGroup container) {
            mDelegate.startUpdate(container);
        }

        @Override
        public @NonNull
        Object instantiateItem(@NonNull ViewGroup container, int position) {
            return mDelegate.instantiateItem(container, position);
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            mDelegate.destroyItem(container, position, object);
        }

        @Override
        public void setPrimaryItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            mDelegate.setPrimaryItem(container, position, object);
        }

        @Override
        public void finishUpdate(@NonNull ViewGroup container) {
            mDelegate.finishUpdate(container);
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return mDelegate.isViewFromObject(view, object);
        }

        @Override
        public Parcelable saveState() {
            return mDelegate.saveState();
        }

        @Override
        public void restoreState(Parcelable state, ClassLoader loader) {
            mDelegate.restoreState(state, loader);
        }

        @Override
        public int getItemPosition(@NonNull Object object) {
            return mDelegate.getItemPosition(object);
        }

        @Override
        public void notifyDataSetChanged() {
            mDelegate.notifyDataSetChanged();
        }

        @Override
        public void registerDataSetObserver(@NonNull DataSetObserver observer) {
            mDelegate.registerDataSetObserver(observer);
        }

        @Override
        public void unregisterDataSetObserver(@NonNull DataSetObserver observer) {
            mDelegate.unregisterDataSetObserver(observer);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mDelegate.getPageTitle(position);
        }

        @Override
        public float getPageWidth(int position) {
            return mDelegate.getPageWidth(position);
        }

        private void superNotifyDataSetChanged() {
            super.notifyDataSetChanged();
        }

        private static class MyDataSetObserver extends DataSetObserver {

            final DelegatingPagerAdapter mParent;

            private MyDataSetObserver(DelegatingPagerAdapter mParent) {
                this.mParent = mParent;
            }

            @Override
            public void onChanged() {
                if (mParent != null) {
                    mParent.superNotifyDataSetChanged();
                }
            }

            @Override
            public void onInvalidated() {
                onChanged();
            }
        }
    }

    private class ReversingOnPageChangeListener implements OnPageChangeListener {

        private final OnPageChangeListener mListener;

        ReversingOnPageChangeListener(OnPageChangeListener listener) {
            mListener = listener;
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            // The documentation says that `getPageWidth(...)` returns the fraction of the _measured_ width that that page takes up.  However, the code seems to
            // use the width so we will here too.
            final int width = getWidth();
            PagerAdapter adapter = RtlViewPager.super.getAdapter();
            if (isRtl() && adapter != null) {
                int count = adapter.getCount();
                int remainingWidth = (int) (width * (1 - adapter.getPageWidth(position))) + positionOffsetPixels;
                while (position < count && remainingWidth > 0) {
                    position += 1;
                    remainingWidth -= (int) (width * adapter.getPageWidth(position));
                }
                position = count - position - 1;
                positionOffsetPixels = -remainingWidth;
                positionOffset = positionOffsetPixels / (width * adapter.getPageWidth(position));
            }
            mListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
        }

        @Override
        public void onPageSelected(int position) {
            PagerAdapter adapter = RtlViewPager.super.getAdapter();
            if (isRtl() && adapter != null) {
                position = adapter.getCount() - position - 1;
            }
            mListener.onPageSelected(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            mListener.onPageScrollStateChanged(state);
        }
    }

    private class ReversingAdapter extends DelegatingPagerAdapter {

        ReversingAdapter(@NonNull PagerAdapter adapter) {
            super(adapter);
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            if (isRtl()) {
                position = getCount() - position - 1;
            }
            super.destroyItem(container, position, object);
        }

        @Override
        public int getItemPosition(@NonNull Object object) {
            int position = super.getItemPosition(object);
            if (isRtl()) {
                if (position == POSITION_UNCHANGED || position == POSITION_NONE) {
                    // We can't accept POSITION_UNCHANGED when in RTL mode because adding items to the end of the collection adds them to the beginning of the
                    // ViewPager.  Items whose positions do not change from the perspective of the wrapped adapter actually do change from the perspective of
                    // the ViewPager.
                    position = POSITION_NONE;
                } else {
                    position = getCount() - position - 1;
                }
            }
            return position;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            if (isRtl()) {
                position = getCount() - position - 1;
            }
            return super.getPageTitle(position);
        }

        @Override
        public float getPageWidth(int position) {
            if (isRtl()) {
                position = getCount() - position - 1;
            }
            return super.getPageWidth(position);
        }

        @Override
        public @NonNull
        Object instantiateItem(@NonNull ViewGroup container, int position) {
            if (isRtl()) {
                position = getCount() - position - 1;
            }
            return super.instantiateItem(container, position);
        }

        @Override
        public void setPrimaryItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            if (isRtl()) {
                position = getCount() - position - 1;
            }
            super.setPrimaryItem(container, position, object);
        }
    }
}
