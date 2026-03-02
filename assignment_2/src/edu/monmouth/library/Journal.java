package edu.monmouth.library;

public class Journal implements LibraryItem {
    private String title;
    private String editor;
    private int volume;
    private StatusType status;

    public Journal(String title, String editor, int volume, StatusType status) throws JournalException {
        setTitle(title);
        setEditor(editor);
        setVolume(volume);
        setStatus(status);
    }

    public void setTitle(String title) throws JournalException {
        if (title == null || title.length() < JournalC.MIN_TITLE_LENGTH)
            throw new JournalException("the journal title is null or empty.");
        this.title = title;
    }

    @Override
    public String getTitle() {
        return title;
    }

    public void setEditor(String editor) throws JournalException {
        if (editor == null || editor.length() < JournalC.MIN_EDITOR_LENGTH)
            throw new JournalException("Editor is null or empty.");
        this.editor = editor;
    }

    public String getEditor() {
        return editor;
    }

    public void setVolume(int volume) throws JournalException {
        if (volume < JournalC.MIN_VOLUME)
            throw new JournalException("Volume must be at least " + JournalC.MIN_VOLUME + ".");
        this.volume = volume;
    }

    public int getVolume() {
        return volume;
    }

    public void setStatus(StatusType status) {
        this.status = status;
    }

    public StatusType getStatus() {
        return status;
    }

    @Override
    public boolean isAvailable() {
        return status == StatusType.ONSHELF;
    }

    @Override
    public void borrowItem() {
        status = StatusType.BORROWED;
    }

    @Override
    public void returnItem() {
        status = StatusType.ONSHELF;
    }

    @Override
    public String toString() {
        return "Journal [title=" + title + ", editor=" + editor + ", volume=" + volume + ", status=" + status + "]";
    }
}