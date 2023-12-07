const express = require('express');
const app = express();


function generateRandomWord() {
  const words = ['manzana', 'banana', 'pelota', 'perro', 'gato', 'flor', 'guitarra', 'mono', 'fruta', 'palta'];
  return words[Math.floor(Math.random() * words.length)];
}


app.get('/api/alias/:name', (req, res) => {
  const name = req.params.name;


  const aliasWords = Array.from({ length: 2 }, generateRandomWord);
  
  aliasWords.push(name);
  aliasWords.sort(() => Math.random() - 0.5);
  const alias = aliasWords.join('.');

  res.json({ alias });
});


const port = 3000;


app.listen(port, () => {
  console.log(`Servidor API iniciado en http://localhost:${port}`);
});
