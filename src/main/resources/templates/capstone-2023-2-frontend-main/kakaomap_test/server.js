const https = require('https');
const fs = require('fs');
const path = require('path');

const options = {
    key: fs.readFileSync('C:\\Windows\\System32\\key.pem'),
    cert: fs.readFileSync('C:\\Windows\\System32\\cert.pem')
};

const server = https.createServer(options, (req, res) => {
    if (req.method === 'GET' && req.url === '/test.html') {
        fs.readFile('./test.html', (err, content) => {
            if (err) {
                res.writeHead(500);
                res.end();
            } else {
                res.writeHead(200, { 'Content-Type': 'text/html' });
                res.end(content, 'utf-8');
            }
        });
    } else {
        res.writeHead(404);
        res.end("Page not found");
    }
});

server.listen(3000, () => {
    console.log('Server running on https://localhost:3000/');
});
