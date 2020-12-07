package io.javaoperatorsdk.operator.processing.dependentresource.deployment;

public class DeploymentInput {



    private final String customResourceUid; // extract this to an interface?
    private final String name;
    private final String imageName;
    private final String imageVersion;
    private final String namespace;
    private final int replicaCount;

    public DeploymentInput(String customResourceUid, String name, String imageName, String imageVersion, String namespace, int replicaCount) {
        this.customResourceUid = customResourceUid;
        this.name = name;
        this.imageName = imageName;
        this.imageVersion = imageVersion;
        this.namespace = namespace;
        this.replicaCount = replicaCount;
    }

    public String getImageName() {
        return imageName;
    }

    public String getImageVersion() {
        return imageVersion;
    }

    public String getName() {
        return name;
    }

    public String getNamespace() {
        return namespace;
    }

    public int getReplicaCount() {
        return replicaCount;
    }

    public String getCustomResourceUid() {
        return customResourceUid;
    }

    public String image() {
        return imageName + ":" + imageVersion;
    }
}
