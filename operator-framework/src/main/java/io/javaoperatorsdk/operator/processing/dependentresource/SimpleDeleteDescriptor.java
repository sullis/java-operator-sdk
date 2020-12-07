package io.javaoperatorsdk.operator.processing.dependentresource;

import io.javaoperatorsdk.operator.api.DeleteControl;

public class SimpleDeleteDescriptor implements DeleteDescriptor{

    private final DeleteControl deleteControl;

    public SimpleDeleteDescriptor(DeleteControl deleteControl) {
        this.deleteControl = deleteControl;
    }

    @Override
    public DeleteControl getDeleteControl() {
        return deleteControl;
    }
}
