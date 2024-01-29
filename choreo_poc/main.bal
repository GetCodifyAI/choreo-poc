import ballerina/io;
import ballerina/ftp;
import ballerina/log;

configurable string host = ?;
configurable int port = ?;
configurable string username = ?;
configurable string password = ?;
configurable string privateKeyPath = ?;
configurable string privateKeyPassword = ?;
configurable string path = ?;
configurable string fileNamePattern = ?;

ftp:ListenerConfiguration config = {
    host,
    port,
    auth: {
        credentials: {username, password},
        privateKey: {path: privateKeyPath, password:privateKeyPassword}

    },
    protocol: ftp:SFTP,
    fileNamePattern,
    path,
    pollingInterval: 10
};

service on new ftp:Listener(config) {
    remote function onFileChange(ftp:WatchEvent & readonly event, ftp:Caller caller) returns error? {
        log:printInfo("File change event received", event = event.addedFiles);
        foreach ftp:FileInfo addedFile in event.addedFiles {
            stream<byte[] & readonly, io:Error?> fileContent = check caller->get(addedFile.pathDecoded);
            // Write the content to a file.
            check io:fileWriteBlocksFromStream(string `./local/${addedFile.name}`, fileContent);
            // Write the content to a file.
            _ = check caller->put("/cut-dry-vendor-integration/choreo-poc/desc/out.csv", check io:fileReadBlocksAsStream(string `./local/${addedFile.name}`));
            check fileContent.close();
        }
    }
}

// ftp:Client ftpClient = check new({
//     host: "sftp-vendors.cutanddry.com",
//     port: 22,
//     auth: {
//         credentials: {username: "choreo-poc", password: ""},
//         privateKey: {path: "./sftp.private.key", password: "changeit"}

//     },
//     protocol: ftp:SFTP
// });
// public function main() returns error? {
//     log:printInfo("Connected to the server");
//     ftp:FileInfo[] files = check ftpClient->list("/cut-dry-vendor-integration/choreo-poc");
//     log:printInfo("Files in the directory", files = files);
// }
