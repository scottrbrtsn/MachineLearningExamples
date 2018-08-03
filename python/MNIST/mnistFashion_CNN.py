import mxnet as mx

def transform(data, label):
    return mx.nd.transpose(data.astype(np.float32), (2,0,1))/255, label.astype(np.float32)

train_data = gluon.data.DataLoader(
    gluon.data.vision.FashionMNIST(train=True, transform=transform), batch_size=128, shuffle=True)

validation_data = gluon.data.DataLoader(
    gluon.data.vision.FashionMNIST(train=False, transform=transform), batch_size=128, shuffle=False)
