@GetMapping("/search")
public Page<Item> searchWithFilters(
        @RequestParam Map<String, String> params,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "20") int size) {

    // remove pagination params from filters
    params.remove("page");
    params.remove("size");

    // convert to Object map (simple version: keep all as String)
    Map<String, Object> filters = new HashMap<>(params);

    return svc.searchWithFilters(filters, page, size);
}
