package io.javaoperatorsdk.operator.processing.dependentresource.deployment;

public class DeleteInput {

    private final String name;
    private final String namespace;


    public DeleteInput(String name, String namespace) {
        this.name = name;
        this.namespace = namespace;
    }

    public String getName() {
        return name;
    }

    public String getNamespace() {
        return namespace;
    }
}
