public abstract class Task {
    protected String description;
    protected boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public abstract String encode();


    @Override
    public String toString() {
        String s = String.format("[%s] %s", this.isDone ? "X" : " ", this.description);
        return s;
    }

    public void markAsDone() {
        this.isDone = true;
    }

    public void unmark() {
        this.isDone = false;
    }

    /**
     * Reconstructs a task from its stored representation.
     *
     * @param line encoded task data
     * @return the reconstructed task
     * @throws NeilException if the stored data is invalid
     */
    public static Task decode(String line) throws NeilException {
        String[] parts = line.split("\\s*\\|\\s*", -1);

        if (parts.length < 3) {
            throw new NeilException("Invalid saved task: " + line);
        }

        String type = parts[0];
        String status = parts[1];
        String description = parts[2];

        if (description.isBlank()) {
            throw new NeilException("Saved task has no description: " + line);
        }

        boolean isDone;

        if (status.equals("1")) {
            isDone = true;
        } else if (status.equals("0")) {
            isDone = false;
        } else {
            throw new NeilException("Invalid saved task status: " + line);
        }

        Task task;

        switch (type) {
            case "T":
                if (parts.length != 3) {
                    throw new NeilException("Invalid saved todo: " + line);
                }

                task = new ToDoTask(description);
                break;

            case "D":
                if (parts.length != 4 || parts[3].isBlank()) {
                    throw new NeilException("Invalid saved deadline: " + line);
                }

                task = new DeadlineTask(description, parts[3]);
                break;

            case "E":
                if (parts.length != 5
                        || parts[3].isBlank()
                        || parts[4].isBlank()) {
                    throw new NeilException("Invalid saved event: " + line);
                }

                task = new EventTask(
                        description,
                        parts[3],
                        parts[4]
                );
                break;

            default:
                throw new NeilException("Unknown saved task type: " + type);
        }

        if (isDone) {
            task.markAsDone();
        }

        return task;
    }
}