package ru.mirea.yudakovam.lesson4;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;

import ru.mirea.yudakovam.lesson4.databinding.ActivityPlayerBinding;


public class PlayerActivity extends AppCompatActivity {

    private ActivityPlayerBinding binding;
    private MediaPlayer mediaPlayer;
    private boolean isPlaying = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPlayerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Имитация загрузки трека
        loadMedia();

        binding.buttonPlayPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPlaying) {
                    pauseMedia();
                } else {
                    playMedia();
                }
            }
        });

        binding.seekBarProgress.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    mediaPlayer.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // При начале перемещения бегунка, пауза воспроизведения
                if (isPlaying) {
                    pauseMedia();
                }
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // При окончании перемещения бегунка, продолжение воспроизведения
                if (mediaPlayer != null && !isPlaying) {
                    playMedia();
                }
            }
        });

        binding.buttonPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pauseMedia();
                // Запуск воспроизведения текущего трека с самого начала
                mediaPlayer.seekTo(0);
                playMedia();
            }
        });

        binding.buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextMedia();
            }
        });
    }

    private void loadMedia() {
        mediaPlayer = MediaPlayer.create(this, R.raw.music);
        binding.seekBarProgress.setMax(mediaPlayer.getDuration());
    }

    private void playMedia() {
        if (!isPlaying) {
            mediaPlayer.start();
            isPlaying = true;
            updateSeekBar();
            updatePlayPauseButton();
        }
    }

    private void pauseMedia() {
        if (isPlaying) {
            mediaPlayer.pause();
            isPlaying = false;
            updatePlayPauseButton();
        }
    }
    private void nextMedia(){
        mediaPlayer.seekTo(0);
        updateSeekBar();
        mediaPlayer.pause();
        isPlaying = false;
        updatePlayPauseButton();
    }

    private void updatePlayPauseButton() {
        if (isPlaying) {
            binding.buttonPlayPause.setImageResource(android.R.drawable.ic_media_pause);
        } else {
            binding.buttonPlayPause.setImageResource(android.R.drawable.ic_media_play);
        }
    }
    private void updateSeekBar() {
        if (mediaPlayer != null && isPlaying) {
            binding.seekBarProgress.setProgress(mediaPlayer.getCurrentPosition());

            // Периодически обновляйте позицию SeekBar в соответствии с проигрываемым треком
            Runnable updateSeekBar = new Runnable() {
                @Override
                public void run() {
                    updateSeekBar();
                }
            };
            binding.seekBarProgress.postDelayed(updateSeekBar, 1000);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null; // Установка mediaPlayer в null для предотвращения утечек памяти
        }
    }
}