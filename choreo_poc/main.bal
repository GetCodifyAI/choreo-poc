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
configurable string fileName = ?;

// ftp:ListenerConfiguration config = {
//     host,
//     port,
//     auth: {
//         credentials: {username, password},
//         privateKey: {path: privateKeyPath, password:privateKeyPassword}

//     },
//     protocol: ftp:SFTP,
//     pollingInterval: 10
// };

// service on new ftp:Listener(config) {
//     remote function onFileChange(ftp:WatchEvent & readonly event, ftp:Caller caller) returns error? {
//         log:printInfo("File change event received", event = event.addedFiles);
//         foreach ftp:FileInfo addedFile in event.addedFiles {
//             stream<byte[] & readonly, io:Error?> fileContent = check caller->get(addedFile.pathDecoded);
//             // Write the content to a file.
//             check io:fileWriteBlocksFromStream(string `./local/${addedFile.name}`, fileContent);
//             // Write the content to a file.
//             _ = check caller->put("/cut-dry-vendor-integration/choreo-poc/desc/out.csv", check io:fileReadBlocksAsStream(string `./local/${addedFile.name}`));
//             check fileContent.close();
//         }
//     }
// }

ftp:ClientConfiguration clientConfig = {
    host,
    port,
    auth: {
        credentials: {username, password},
        privateKey: {path: privateKeyPath, password:privateKeyPassword}
    },
    protocol: ftp:SFTP
};
ftp:Client ftpClient = check new(clientConfig);

public function main() returns error? {
    log:printInfo("Starting FTP server connector client");
    // Read the content of the file.
    stream<byte[], io:Error?> fileContent = check ftpClient->get(string `${path}/${fileName}`);
    // Write the content to a file.
    check io:fileWriteBlocksFromStream(string `./local/${fileName}`, fileContent);
    // Write the content to a file.
    _ = check ftpClient->put(string `/cut-dry-vendor-integration/choreo-poc/desc/${fileName}`, check io:fileReadBlocksAsStream(string `./local/${fileName}`));
    check fileContent.close();
}
