package com.xenione.digit;

/**
 * rotates middle tab downwards
 */
public final class TabAnimationDown extends AbstractTabAnimation {

    public TabAnimationDown(TabDigit.Tab mTopTab, TabDigit.Tab mBottomTab, TabDigit.Tab mMiddleTab, float thickness) {
        super(mTopTab, mBottomTab, mMiddleTab, thickness);
    }

    public void initState() {
        state = UPPER_POSITION;
    }

    @Override
    public void initMiddleTab() {
        mMiddleTab.setPivotYOffset(-mThickness / 2);
        mMiddleTab.rotate(180);
    }

    @Override
    public void run() {

        if (mTime == -1) {
            return;
        }

        switch (state) {
            case LOWER_POSITION: {
                if (mAlpha <= 0) {
                    mBottomTab.next();
                    state = UPPER_POSITION;
                    mTime = -1; // animation finished
                }
                break;
            }
            case MIDDLE_POSITION: {
                if (mAlpha < 90) {
                    mMiddleTab.next();
                    mMiddleTab.setPivotYOffset(mThickness / 2);
                    state = LOWER_POSITION;
                }
                break;
            }
            case UPPER_POSITION: {
                mTopTab.next();
                mMiddleTab.setPivotYOffset(-mThickness / 2);
                state = MIDDLE_POSITION;
                break;
            }
        }

        if (mTime != -1) {
            long delta = (System.currentTimeMillis() - mTime);
            mAlpha = 180 - (int) (180 * (1 - (1 * mElapsedTime - delta) / (1 * mElapsedTime)));
            mMiddleTab.rotate(mAlpha);
        }

    }

    @Override
    protected void makeSureCycleIsClosed() {
        if (mTime == -1) {
            return;
        }
        switch (state) {
            case LOWER_POSITION: {
                mBottomTab.next();
                state = UPPER_POSITION;
                mTime = -1; // animation finished
            }
        }
        mMiddleTab.setPivotYOffset(-mThickness / 2);
        mMiddleTab.rotate(180);
    }
}
