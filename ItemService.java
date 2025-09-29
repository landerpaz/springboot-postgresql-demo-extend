@Transactional(readOnly = true)
public Page<Item> searchWithFilters(Map<String, Object> filters, int page, int size) {
    int pageSize = Math.min(size, maxPageSize);
    PageRequest pr = PageRequest.of(Math.max(0, page), pageSize);
    return repo.searchByFilters(filters, pr);
}
