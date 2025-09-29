GET /api/items/search?name=foo&description=bar&page=0&size=10
→ WHERE lower(name) LIKE '%foo%' AND lower(description) LIKE '%bar%'


GET /api/items/search?id=5
→ WHERE id = 5


GET /api/items/search?name=foo&id=3
→ WHERE id = 3 AND lower(name) LIKE '%foo%'

