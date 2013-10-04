package com.panjiesw.anywherepic.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * <p>
 *     Base class for MainActivity's content fragment.
 *     Displays contents according to feedback from top level view. <BR/>
 *     Whenever a new set of contents need to displayed, extends this class,
 *     provide content specific function and it will be listed in
 *     top level navigation drawer.
 * <p/>
 * <p>
 *     Child implementation MUST provide a public accessible String TAG
 *     to be used in parent activity's fragment transaction as tag name.
 * </p>
 * Blangszutan Project<BR/>
 * Project: AnywherePic<BR/>
 * Created At: 10/5/13 1:50 AM<BR/>
 *
 * @author Panjie SW
 */
public abstract class ContentFragment extends Fragment {
    private static final String TAG = "ContentFragment";

    protected ContentFragmentListener mListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Try to attach activity as listener, or throw
        // a class cast exception if the parent activity doesn't
        // implement this fragment's listener interface.
        try {
            mListener = (ContentFragmentListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(
                    activity.toString() + " must implement ContentFragmentListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    /**
     * Method to gather the data into displayable content.
     * Child class must implement this and call from reasonable
     * logic location, e.g. to initialize an adapter of AdapterView.
     */
    protected abstract void gatherContents();

    /**
     * Listener for any action originated from this Fragment.
     * Must be implemented in parent Activity to enable data
     * communication between this Fragment, Activity and/or
     * other Fragment involved.
     */
    public interface ContentFragmentListener {

        /**
         * Called when an item in this Fragment received any
         * action from user and need to pass itself to parent
         * Activity for further process.
         * @param fragment The child Fragment class this action is called from.
         * @param item The item to pass to.
         */
        public void onContentAction(ContentFragment fragment, Object item);
    }
}
