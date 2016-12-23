package com.eminayar.panter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.ArrayRes;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.eminayar.panter.adapters.SingleChoiceAdapter;
import com.eminayar.panter.enums.Animation;
import com.eminayar.panter.interfaces.OnDialogClickListener;
import com.eminayar.panter.interfaces.OnSingleCallbackConfirmListener;
import com.eminayar.panter.interfaces.OnTextInputConfirmListener;

import java.util.ArrayList;

public class PanterDialog extends Dialog {

    private Context context;
    //Views
    private EditText input;
    private TextView message;
    private TextView positive;
    private TextView negative;
    private TextView title;
    private ImageView headerImageView;
    private ImageView headerLogoImageView;
    private RelativeLayout header;
    private RecyclerView recyclerView;
    private View divider;
    //Values
    private boolean isCancelable = false;
    private boolean isInputDialog = false;
    private String positiveText;
    private String negativeText;
    private String messageText;
    private String titleText;
    private String inputHint;
    private String emptyErrorText;
    private int titleSize = Constants.DEFAULT_TITLE_SIZE;
    private int titleColor = Constants.DEFAULT_COLOR;
    private int headerPattern = Constants.NO_BACKGROUND;
    private Drawable headerPatternDrawable;
    private int headerLogo = Constants.NO_LOGO;
    private Drawable headerLogoDrawable;
    private DialogType dialogType = DialogType.STANDART;
    private Animation animationType = Animation.DEFAULT;
    //Listeners
    private View.OnClickListener positiveListener;
    private View.OnClickListener negativeListener;
    private View.OnClickListener inputListener;
    private View.OnClickListener choiceListener;
    private View.OnClickListener dismissListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            dismiss();
        }
    };
    //Adapters
    private SingleChoiceAdapter singleChoiceAdapter;


    public PanterDialog(Context context) {
        super(context);
        this.context = context;
    }

    public PanterDialog(Context context, int themeResId) {
        super(context, themeResId);
        this.context = context;
    }

    protected PanterDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // We want background transparent because users can use radius images on headers
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        //Set animation before layouts created
        setAnimation();
        setContentView(R.layout.dialog);

        // This is RelativeLayout for header
        header = (RelativeLayout) findViewById(R.id.header);
        // This is divider between buttons
        divider = (View) findViewById(R.id.button_divider);
        // This is ImageView for header pattern
        headerImageView = (ImageView) findViewById(R.id.header_pattern);
        // This is ImageView for logo on header
        headerLogoImageView = (ImageView) findViewById(R.id.pattern_logo);
        // This is textview for content
        message = (TextView) findViewById(R.id.message);
        // This is textview (fake button) for positive button
        positive = (TextView) findViewById(R.id.positive);
        // This is textview (fake button) for negative button
        negative = (TextView) findViewById(R.id.negative);
        // This is textview for title
        title = (TextView) findViewById(R.id.header_title);
        // This is edittext for input
        input = (EditText) findViewById(R.id.input);
        // This is recyclerView for list dialogs
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        /**
         * If we dont do this configuration dialog's are getting %80 of screen width
         * Now it is getting %90 of screen width, but we can also set it also programmatically
         */
        ViewGroup.LayoutParams params = getWindow().getAttributes();
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);
        // Set messages and listeners for messages and buttons
        init();
    }

    private void setAnimation() {
        switch (animationType) {
            case DEFAULT:
                break;
            case SLIDE:
                getWindow().getAttributes().windowAnimations = R.style.SlideAnimation;
                break;
            case POP:
                getWindow().getAttributes().windowAnimations = R.style.PopAnimation;
                break;
            case SIDE:
                getWindow().getAttributes().windowAnimations = R.style.SideAnimation;
                break;
        }
    }

    private void init() {
        setCancelable(isCancelable);
        setDialogType();
        setHeaderAndLogo();
        setButtonsAndMessage();
    }

    /**
     * In future because also new types will be added
     * I am planning to handle all types in one layout so this is why I'm configuring content
     * area in this method
     */
    private void setDialogType() {
        switch (dialogType) {
            case STANDART:
                input.setVisibility(View.GONE);
                recyclerView.setVisibility(View.GONE);
                isInputDialog = false;
                break;
            case INPUT:
                message.setVisibility(View.GONE);
                input.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
                isInputDialog = true;
                break;
            case SINGLECHOICE:
                message.setVisibility(View.GONE);
                input.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                isInputDialog = false;
                break;
        }
    }

    /**
     * This method checks whether any logo or header background passed to app or not
     * According them it shows or hide views
     */
    private void setHeaderAndLogo() {
        boolean showLogo = true;
        boolean showPattern = true;
        // If header was not set , set it's visibility to gone
        if (headerPattern == Constants.NO_BACKGROUND && headerPatternDrawable == null) {
            header.setVisibility(View.GONE);
            showPattern = false;
        } else if (headerPattern != Constants.NO_BACKGROUND) {
            headerImageView.setImageResource(headerPattern);
        } else {
            headerImageView.setImageDrawable(headerPatternDrawable);
        }
        // If header logo was not set , set it's visibility to gone
        if (headerLogo == Constants.NO_LOGO && headerLogoDrawable == null) {
            headerLogoImageView.setVisibility(View.GONE);
            showLogo = false;
        } else if (headerLogo != Constants.NO_LOGO) {
            headerLogoImageView.setImageResource(headerLogo);
        } else {
            headerLogoImageView.setImageDrawable(headerLogoDrawable);
        }
        if (!showLogo && showPattern && !TextUtils.isEmpty(titleText)) {
            title.setVisibility(View.VISIBLE);
            title.setTextSize(TypedValue.COMPLEX_UNIT_SP, titleSize);
            title.setTextColor(titleColor);
            title.setText(titleText);
        }
    }

    /**
     * This function is configuring buttons visibilities' & texts and message text
     * If there is no text for buttons it shows only positive button with default positive text
     * And it set dismiss listener as click listener
     */
    private void setButtonsAndMessage() {
        boolean showPos = false;
        boolean showNeg = false;
        if (TextUtils.isEmpty(positiveText) && TextUtils.isEmpty(negativeText) && !isInputDialog) {
            positiveText = context.getString(R.string.dialog_positive);
            positiveListener = dismissListener;
            showPos = true;
        }
        if (TextUtils.isEmpty(positiveText) && TextUtils.isEmpty(negativeText) && dialogType ==
                DialogType.INPUT) {
            positiveText = context.getString(R.string.dialog_positive);
            showPos = true;
        }
        if (TextUtils.isEmpty(positiveText) && TextUtils.isEmpty(negativeText) && dialogType ==
                DialogType.SINGLECHOICE) {
            positiveText = context.getString(R.string.dialog_positive_single_selection_default);
            showPos = true;
        }
        if (!TextUtils.isEmpty(negativeText)) {
            showNeg = true;
        }
        if (!TextUtils.isEmpty(positiveText)) {
            showPos = true;
        }
        if (!showPos || !showNeg) {
            divider.setVisibility(View.GONE);
        }
        if (showPos) {
            switch (dialogType) {
                case INPUT:
                    positiveListener = inputListener;
                    break;
                case SINGLECHOICE:
                    positiveListener = choiceListener;
                    break;
                default:
                    break;
            }
            positive.setText(positiveText);
            positive.setOnClickListener(positiveListener);
        }
        if (showNeg) {
            negative.setText(negativeText);
            negative.setOnClickListener(negativeListener);
        }
        LinearLayoutManager manager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(manager);
        if (singleChoiceAdapter != null)
            recyclerView.setAdapter(singleChoiceAdapter);
        message.setText(messageText);
        input.setHint(inputHint);
    }

    /**
     * Set message text to our content text view
     * We can directly pass String text to textview
     *
     * @param text
     * @return
     */
    public PanterDialog setMessage(String text) {
        messageText = text;
        return this;
    }

    /**
     * Set message text to our content text view
     * We can directly pass String id to textview
     *
     * @param textId
     * @return
     */
    public PanterDialog setMessage(int textId) {
        messageText = context.getString(textId);
        return this;
    }

    /**
     * Set positive button text
     * If there is no listener passed to method
     * dialog will be dismissed as default behaivour
     *
     * @param text
     * @return
     */
    public PanterDialog setPositive(String text) {
        positiveText = text;
        positiveListener = dismissListener;
        return this;
    }

    /**
     * Set positive button text
     * Listener passed to this method
     *
     * @param text
     * @return
     */
    public PanterDialog setPositive(String text, OnDialogClickListener listener) {
        positiveText = text;
        positiveListener = new ButtonClickCallback(listener);
        return this;
    }

    /**
     * Set negative button text
     * If there is no listener passed to method
     * dialog will be dismissed as default behaivour
     *
     * @param text
     * @return
     */
    public PanterDialog setNegative(String text) {
        negativeText = text;
        negativeListener = dismissListener;
        return this;
    }

    /**
     * Set negative button text
     * Listener passed to this method
     *
     * @param text
     * @return
     */
    public PanterDialog setNegative(String text, OnDialogClickListener listener) {
        negativeText = text;
        negativeListener = new ButtonClickCallback(listener);
        return this;
    }

    /**
     * Set title at header
     *
     * @param title
     * @return
     */
    public PanterDialog setTitle(String title) {
        this.titleText = title;
        return this;
    }

    /**
     * Set title at header
     * Set textSize of title
     *
     * @param title
     * @return
     */
    public PanterDialog setTitle(String title, int textSize) {
        this.titleText = title;
        this.titleSize = textSize;
        return this;
    }

    /**
     * Set title at header
     * Set color of title
     * Set textSize of title
     *
     * @param title
     * @return
     */
    public PanterDialog setTitle(String title, int textSize, int color) {
        this.titleText = title;
        this.titleSize = textSize;
        this.titleColor = color;
        return this;
    }

    /**
     * Set title at header
     * Set color of title
     *
     * @param title
     * @return
     */
    public PanterDialog setTitleWithColor(String title, int color) {
        this.titleText = title;
        this.titleColor = color;
        return this;
    }

    /**
     * Set title size
     *
     * @param size
     * @return
     */
    public PanterDialog setTitleSize(int size) {
        this.titleSize = size;
        return this;
    }

    /**
     * Set title color
     *
     * @param color
     * @return
     */
    public PanterDialog setTitleColor(int color) {
        this.titleColor = color;
        return this;
    }

    /**
     * Pass background id from drawable
     *
     * @param headerBackground
     * @return
     */
    public PanterDialog setHeaderBackground(int headerBackground) {
        this.headerPattern = headerBackground;
        return this;
    }

    /**
     * Pass background drawable
     *
     * @param headerBackground
     * @return
     */
    public PanterDialog setHeaderBackground(Drawable headerBackground) {
        this.headerPatternDrawable = headerBackground;
        return this;
    }

    /**
     * Pass header logo id from drawable folder
     *
     * @param logo
     * @return
     */
    public PanterDialog setHeaderLogo(int logo) {
        this.headerLogo = logo;
        return this;
    }

    /**
     * Pass directly header logo drawable
     *
     * @param logo
     * @return
     */
    public PanterDialog setHeaderLogo(Drawable logo) {
        this.headerLogoDrawable = logo;
        return this;
    }

    /**
     * Set cancelable flag of dialog
     *
     * @param flag
     * @return
     */
    public PanterDialog isCancelable(boolean flag) {
        this.isCancelable = flag;
        return this;
    }

    /**
     * Set type of this dialog from predefined enum
     *
     * @param type
     * @return
     */
    public PanterDialog setDialogType(DialogType type) {
        this.dialogType = type;
        return this;
    }

    /**
     * Set hint and text callback as parameter
     *
     * @param hint
     * @param listener
     * @return
     */
    public PanterDialog input(String hint, OnTextInputConfirmListener listener) {
        this.inputHint = hint;
        this.inputListener = new TextInputListener(listener);
        return this;
    }

    /**
     * Set text callback as parameter
     *
     * @param listener
     * @return
     */
    public PanterDialog input(OnTextInputConfirmListener listener) {
        this.inputListener = new TextInputListener(listener);
        return this;
    }

    /**
     * Set single choice dialog with resources array
     *
     * @param array
     * @param listener
     * @return
     */
    public PanterDialog items(@ArrayRes int array, OnSingleCallbackConfirmListener listener) {
        String[] mArray = context.getResources().getStringArray(array);
        singleChoiceAdapter = new SingleChoiceAdapter(mArray);
        this.choiceListener = new SingleListCallback(listener);
        return this;
    }

    /**
     * Set single choice dialog with given string array
     *
     * @param array
     * @param listener
     * @return
     */
    public PanterDialog items(String[] array, OnSingleCallbackConfirmListener listener) {
        singleChoiceAdapter = new SingleChoiceAdapter(array);
        this.choiceListener = new SingleListCallback(listener);
        return this;
    }

    /**
     * Set single choice dialog with given string arraylist
     *
     * @param array
     * @param listener
     * @return
     */
    public PanterDialog items(ArrayList<String> array, OnSingleCallbackConfirmListener listener) {
        String[] mArray = array.toArray(new String[0]);
        singleChoiceAdapter = new SingleChoiceAdapter(mArray);
        this.choiceListener = new SingleListCallback(listener);
        return this;
    }

    /**
     * Set hint, empty error string and callback as parameter
     *
     * @param emptyErrorText
     * @param hint
     * @param listener
     * @return
     */
    public PanterDialog input(String hint, String emptyErrorText, OnTextInputConfirmListener
            listener) {
        this.emptyErrorText = emptyErrorText;
        this.inputHint = hint;
        this.inputListener = new TextInputListener(listener);
        return this;
    }

    /**
     * Set Animation type as enum
     *
     * @param animation
     * @return
     */
    public PanterDialog withAnimation(Animation animation) {
        this.animationType = animation;
        return this;
    }

    /**
     * This click listener is build up to get text on input dialogs
     * When user enter their input and clicked on positive buttons it will automatically
     * pass writtent text to users
     */
    private class TextInputListener implements View.OnClickListener {

        private OnTextInputConfirmListener wrapped;

        private TextInputListener(OnTextInputConfirmListener wrapped) {
            this.wrapped = wrapped;
        }

        @Override
        public void onClick(View v) {
            String text = input.getText().toString();
            if (dialogType == DialogType.INPUT && TextUtils.isEmpty(text)) {
                if (TextUtils.isEmpty(emptyErrorText)) {
                    dismiss();
                    return;
                }
                input.setError(emptyErrorText);
                return;
            }

            if (wrapped != null) {
                wrapped.onTextInputConfirmed(text);
            }

            dismiss();
        }
    }

    /**
     * This click listener is build up to get single choice from user
     * When userselect a radio button and clicked on positive buttons it will automatically
     * pass dialog object, text and position to users
     * if there is no selection then it will dismiss dialog
     */
    private class SingleListCallback implements View.OnClickListener {

        private OnSingleCallbackConfirmListener wrapped;

        private SingleListCallback(OnSingleCallbackConfirmListener wrapped) {
            this.wrapped = wrapped;
        }

        @Override
        public void onClick(View v) {

            if (wrapped != null) {
                if (singleChoiceAdapter.lastCheckedPosition <= -1) {
                    dismiss();
                    return;
                }
                wrapped.onSingleCallbackConfirmed(PanterDialog.this, singleChoiceAdapter
                        .lastCheckedPosition, singleChoiceAdapter.list[singleChoiceAdapter.lastCheckedPosition]);
            }
            dismiss();
        }
    }

    /**
     * This click listener is build up to used as normal click listeners
     * It will automatically dismiss dialog after clicked
     */
    private class ButtonClickCallback implements View.OnClickListener {

        private OnDialogClickListener wrapped;

        private ButtonClickCallback(OnDialogClickListener wrapped) {
            this.wrapped = wrapped;
        }

        @Override
        public void onClick(View v) {

            if (wrapped != null) {
                wrapped.onDialogButtonClicked(PanterDialog.this);
            }
            dismiss();
        }
    }
}
