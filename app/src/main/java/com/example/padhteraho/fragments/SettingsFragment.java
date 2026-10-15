package com.example.padhteraho.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.app.AlertDialog;
import android.widget.RatingBar;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.padhteraho.MainActivity;
import com.example.padhteraho.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.animation.OvershootInterpolator;
import android.content.SharedPreferences;

public class SettingsFragment extends Fragment {

    private TextView txtVersion;

    private TextView itemDarkMode;
    private TextView itemShare;
    private TextView itemFeedback;
    private TextView itemPrivacy;
    private TextView itemTerms;
    private TextView itemContact;
    private TextView itemSupport;
    private TextView itemRequestCourse;
    private TextView itemFaq;
    private TextView itemUpdate;
    private TextView itemRate;
    private SharedPreferences preferences;

    private static final String SUPPORT_EMAIL = "sainiaditya115@gmail.com";

    private int tapCount = 0;
    private long lastTapTime = 0;

    public SettingsFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        preferences = requireContext()
                .getSharedPreferences("settings", requireContext().MODE_PRIVATE);

        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        txtVersion = view.findViewById(R.id.txtVersion);

        itemDarkMode = view.findViewById(R.id.itemDarkMode);
        itemShare = view.findViewById(R.id.itemShare);
        itemFeedback = view.findViewById(R.id.itemFeedback);
        itemPrivacy = view.findViewById(R.id.itemPrivacy);
        itemTerms = view.findViewById(R.id.itemTerms);
        itemContact = view.findViewById(R.id.itemContact);
        itemSupport = view.findViewById(R.id.itemSupport);
        itemRequestCourse = view.findViewById(R.id.itemRequestCourse);
        itemFaq = view.findViewById(R.id.itemFaq);
        itemUpdate = view.findViewById(R.id.itemUpdate);
        itemRate = view.findViewById(R.id.itemRate);

        setupClicks();

        return view;
    }

    private void setupClicks() {

//         Dark Mode
        itemDarkMode.setOnClickListener(v -> {
                    Toast.makeText(getContext(),
                            "This Feature Coming Soon.",
                            Toast.LENGTH_SHORT).show();
        });

        // Share App
        itemShare.setOnClickListener(v -> {

            Intent shareIntent = new Intent(Intent.ACTION_SEND);

            shareIntent.setType("text/plain");

            String shareMessage =
                    "📚 Learn Programming for FREE with PadhteRaho!\n\n"
                            + "Download the latest version here:\n\n"
                            + "https://github.com/adityasaini404/PadhteRaho/releases/latest\n\n"
                            + "Happy Learning 🚀";

            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);

            startActivity(Intent.createChooser(
                    shareIntent,
                    "Share PadhteRaho"));

        });
        // Rate
        itemRate.setOnClickListener(v -> {

            BottomSheetDialog dialog =
                    new BottomSheetDialog(requireContext());

            View dialogView = LayoutInflater.from(getContext())
                    .inflate(R.layout.dialog_rate, null);

            dialog.setContentView(dialogView);

            RatingBar ratingBar =
                    dialogView.findViewById(R.id.ratingBar);

            MaterialButton btnSubmit =
                    dialogView.findViewById(R.id.btnSubmitRating);

            TextView btnLater =
                    dialogView.findViewById(R.id.btnLater);

            btnLater.setOnClickListener(view -> dialog.dismiss());

            btnSubmit.setOnClickListener(view -> {

                float rating = ratingBar.getRating();

                if (rating == 0) {

                    Toast.makeText(
                            getContext(),
                            "Please select a rating",
                            Toast.LENGTH_SHORT
                    ).show();

                    return;
                }

                Toast.makeText(
                        getContext(),
                        "⭐ Thanks for your " + (int) rating + " star rating!",
                        Toast.LENGTH_LONG
                ).show();

                dialog.dismiss();

            });

            dialog.show();

        });

        // Feedback
        itemFeedback.setOnClickListener(v -> {

            String message =
                    "Hi Aditya 👋%0A%0A"
                            + "I want to share feedback about the PadhteRaho app.%0A%0A"
                            + "Feedback:%0A";

            Intent intent = new Intent(Intent.ACTION_VIEW);

            intent.setData(Uri.parse(
                    "https://wa.me/916392220656?text=" + message));

            try {

                startActivity(intent);

            } catch (Exception e) {

                Toast.makeText(
                        getContext(),
                        "WhatsApp is not installed.",
                        Toast.LENGTH_SHORT
                ).show();

            }

        });

        // Privacy
        itemPrivacy.setOnClickListener(v -> {

            BottomSheetDialog dialog =
                    new BottomSheetDialog(requireContext());

            View view = LayoutInflater.from(getContext())
                    .inflate(R.layout.dialog_privacy, null);

            dialog.setContentView(view);

            TextView txtPrivacy =
                    view.findViewById(R.id.txtPrivacy);

            MaterialButton btnClose =
                    view.findViewById(R.id.btnClosePrivacy);

            String privacy =

                    "PadhteRaho Privacy Policy\n\n"

                            + "1. PadhteRaho does not collect your personal information.\n\n"

                            + "2. We do not ask for login, passwords, or payment details.\n\n"

                            + "3. All courses available in the app are publicly accessible educational resources.\n\n"

                            + "4. The app may require internet access to load course information.\n\n"

                            + "5. We never sell or share your personal data with third parties.\n\n"

                            + "6. Future updates may introduce optional features such as saved courses or progress tracking. Those features will always respect your privacy.\n\n"

                            + "7. By using PadhteRaho, you agree to this Privacy Policy.\n\n"

                            + "If you have any questions, please contact us through the Contact Us section in Settings.\n\n"

                            + "Last Updated: July 2026";

            txtPrivacy.setText(privacy);

            btnClose.setOnClickListener(view1 -> dialog.dismiss());

            dialog.show();

        });

        // Terms
        itemTerms.setOnClickListener(v -> {

            BottomSheetDialog dialog =
                    new BottomSheetDialog(requireContext());

            View view = LayoutInflater.from(getContext())
                    .inflate(R.layout.dialog_terms, null);

            dialog.setContentView(view);

            TextView txtTerms =
                    view.findViewById(R.id.txtTerms);

            MaterialButton btnClose =
                    view.findViewById(R.id.btnCloseTerms);

            String terms =

                    "PadhteRaho Terms & Conditions\n\n"

                            + "1. PadhteRaho is an educational platform created to help students discover free learning resources.\n\n"

                            + "2. All course content belongs to its respective creators. PadhteRaho does not claim ownership of any YouTube videos or playlists.\n\n"

                            + "3. Users must use the app only for educational purposes.\n\n"

                            + "4. We are not responsible for any changes, removal, or availability of third-party content.\n\n"

                            + "5. The app is provided 'as is' without any guarantees or warranties.\n\n"

                            + "6. We may improve or update the application at any time.\n\n"

                            + "7. Continued use of the app means you agree to these Terms & Conditions.\n\n"

                            + "Thank you for using PadhteRaho ❤️";

            txtTerms.setText(terms);

            btnClose.setOnClickListener(view1 -> dialog.dismiss());

            dialog.show();

        });

        // Contact
        itemContact.setOnClickListener(v -> {

            BottomSheetDialog dialog =
                    new BottomSheetDialog(requireContext());

            View dialogView = LayoutInflater.from(getContext())
                    .inflate(R.layout.dialog_contact, null);

            dialog.setContentView(dialogView);

            CardView whatsapp =
                    dialogView.findViewById(R.id.btnWhatsapp);

            CardView sms =
                    dialogView.findViewById(R.id.btnSms);

            CardView instagram =
                    dialogView.findViewById(R.id.btnInstagram);

            whatsapp.setOnClickListener(view -> {

                Intent intent = new Intent(Intent.ACTION_VIEW);

                intent.setData(Uri.parse(
                        "https://wa.me/916392220656"));

                startActivity(intent);

                dialog.dismiss();

            });

            sms.setOnClickListener(view -> {

                Intent intent = new Intent(Intent.ACTION_SENDTO);

                intent.setData(Uri.parse("smsto:916392220656"));

                intent.putExtra("sms_body",
                        "Hello PadhteRaho Team!");

                startActivity(intent);

                dialog.dismiss();

            });

            instagram.setOnClickListener(view -> {

                Intent intent = new Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://instagram.com/@adi.techs"));

                startActivity(intent);

                dialog.dismiss();

            });

            dialog.show();

        });

        // Support
        itemSupport.setOnClickListener(v -> {

            View dialogView = LayoutInflater.from(requireContext())
                    .inflate(R.layout.dialog_support_developer, null);

            MaterialButton btnCoffee = dialogView.findViewById(R.id.btnCoffee);
            MaterialButton btnGithub = dialogView.findViewById(R.id.btnGithub);
            MaterialButton btnInstagram = dialogView.findViewById(R.id.btnInstagram);


            AlertDialog dialog = new AlertDialog.Builder(requireContext())
                    .setView(dialogView)
                    .create();

            dialog.show();

            if (dialog.getWindow() != null) {
                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            }

            // Buy Me a Coffee (UPI Payment)
            btnCoffee.setOnClickListener(view -> {

                animateButton(view);

                String upiUri =
                        "upi://pay" +
                                "?pa=adityasaini0004@okaxis" +
                                "&pn=Aditya%20Saini" +
                                "&tn=Support%20PadhteRaho" +
                                "&am=1" +
                                "&cu=INR";

                Intent intent = new Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse(upiUri)
                );

                try {
                    startActivity(Intent.createChooser(intent, "Pay using"));
                } catch (Exception e) {
                    Toast.makeText(
                            getContext(),
                            "No UPI app found",
                            Toast.LENGTH_SHORT
                    ).show();
                }

            });

            // GitHub
            btnGithub.setOnClickListener(view -> {

                animateButton(v);

                Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://github.com/adityasaini404"));

                startActivity(intent);

            });

            // Instagram
            btnInstagram.setOnClickListener(view -> {

                Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://instagram.com/adi.techs"));

                startActivity(intent);

            });

        });

        // Request Course

        itemRequestCourse.setOnClickListener(v -> {

            BottomSheetDialog dialog = new BottomSheetDialog(requireContext());

            View dialogView = LayoutInflater.from(getContext())
                    .inflate(R.layout.dialog_request_course, null);

            dialog.setContentView(dialogView);

            TextInputEditText edtCourse =
                    dialogView.findViewById(R.id.edtCourse);

            TextInputEditText edtTeacher =
                    dialogView.findViewById(R.id.edtTeacher);

            MaterialButton btnSend =
                    dialogView.findViewById(R.id.btnSendRequest);

            btnSend.setOnClickListener(view -> {

                String course = edtCourse.getText().toString().trim();
                String teacher = edtTeacher.getText().toString().trim();

                if (course.isEmpty()) {

                    edtCourse.setError("Enter course name");
                    edtCourse.requestFocus();
                    return;
                }

                String message =
                        "📢 *Course Request*%0A%0A" +
                                "📚 Course : " + course + "%0A" +
                                "👨‍🏫 Teacher : " +
                                (teacher.isEmpty() ? "Not specified" : teacher);

                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(
                        "https://wa.me/916392220656?text=" + message));

                startActivity(intent);
                dialog.dismiss();
            });

            dialog.show();
        });

        // FAQ
        itemFaq.setOnClickListener(v -> {

            BottomSheetDialog dialog =
                    new BottomSheetDialog(requireContext());

            View view = LayoutInflater.from(getContext())
                    .inflate(R.layout.dialog_faq, null);

            dialog.setContentView(view);

            MaterialButton btnClose =
                    view.findViewById(R.id.btnCloseFaq);

            btnClose.setOnClickListener(x -> dialog.dismiss());

            dialog.show();

        });

        // Update
        itemUpdate.setOnClickListener(v ->
                Toast.makeText(getContext(),
                        "You are using latest version.",
                        Toast.LENGTH_SHORT).show());

        // Hidden Admin Login
        txtVersion.setOnClickListener(v -> {

            long currentTime = System.currentTimeMillis();

            if (currentTime - lastTapTime > 2000) {
                tapCount = 0;
            }

            lastTapTime = currentTime;

            tapCount++;

            if (tapCount == 7) {

                tapCount = 0;

                Toast.makeText(getContext(),
                        "Admin Mode Enabled",
                        Toast.LENGTH_SHORT).show();

                ((MainActivity) requireActivity())
                        .openFragment(new AdminLoginFragment(), null);
            }

        });

    }
    private void animateButton(View view) {

        ObjectAnimator scaleXDown =
                ObjectAnimator.ofFloat(view, View.SCALE_X, 1f, 0.94f);

        ObjectAnimator scaleYDown =
                ObjectAnimator.ofFloat(view, View.SCALE_Y, 1f, 0.94f);

        scaleXDown.setDuration(90);
        scaleYDown.setDuration(90);

        ObjectAnimator scaleXUp =
                ObjectAnimator.ofFloat(view, View.SCALE_X, 0.94f, 1f);

        ObjectAnimator scaleYUp =
                ObjectAnimator.ofFloat(view, View.SCALE_Y, 0.94f, 1f);

        scaleXUp.setDuration(220);
        scaleYUp.setDuration(220);

        OvershootInterpolator interpolator =
                new OvershootInterpolator(2.2f);

        scaleXUp.setInterpolator(interpolator);
        scaleYUp.setInterpolator(interpolator);

        AnimatorSet down = new AnimatorSet();
        down.playTogether(scaleXDown, scaleYDown);

        AnimatorSet up = new AnimatorSet();
        up.playTogether(scaleXUp, scaleYUp);

        AnimatorSet total = new AnimatorSet();
        total.playSequentially(down, up);

        total.start();
    }
}