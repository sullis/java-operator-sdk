package io.javaoperatorsdk.operator.processing.dependentresource;

public class SimpleStatus implements StatusDescriptor {

    private Status status;

    public SimpleStatus(Status status) {
        this.status = status;
    }

    @Override
    public Status getStatus() {
        return status;
    }
}
