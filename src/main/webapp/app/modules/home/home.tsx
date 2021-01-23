import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, ICrudGetAllAction, TextFormat, getSortState, IPaginationBaseState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from '../../entities/production/production.reducer';
import { IProduction } from 'app/shared/model/production.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import './home.scss';
export interface IProductionProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const Home = (props: IProductionProps) => {
  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(props.location, ITEMS_PER_PAGE), props.location.search)
  );

  const getAllEntities = () => {
    props.getEntities(paginationState.activePage - 1, paginationState.itemsPerPage, `${paginationState.sort},${paginationState.order}`);
  };

  const sortEntities = () => {
    getAllEntities();
    const endURL = `?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`;
    if (props.location.search !== endURL) {
      props.history.push(`${props.location.pathname}${endURL}`);
    }
  };

  useEffect(() => {
    sortEntities();
  }, [paginationState.activePage, paginationState.order, paginationState.sort]);

  useEffect(() => {
    const params = new URLSearchParams(props.location.search);
    const page = params.get('page');
    const sort = params.get('sort');
    if (page && sort) {
      const sortSplit = sort.split(',');
      setPaginationState({
        ...paginationState,
        activePage: +page,
        sort: sortSplit[0],
        order: sortSplit[1],
      });
    }
  }, [props.location.search]);

  const sort = p => () => {
    setPaginationState({
      ...paginationState,
      order: paginationState.order === 'asc' ? 'desc' : 'asc',
      sort: p,
    });
  };

  const handlePagination = currentPage =>
    setPaginationState({
      ...paginationState,
      activePage: currentPage,
    });

  const { productionList, match, loading, totalItems } = props;
  return (
    <div className="home-root">
      <div className="sidebar__container">dsjdisa</div>
      <div className="card-items__wrapper">
        <div className="card-items__container">
          {productionList && productionList.length > 0 ? (
            <>
              {productionList.map((production, i) => (
                <div key={`entity-${i}`} className="card-item">
                  <p>
                    <Button tag={Link} to={`${match.url}/${production.id}`} color="link" size="sm">
                      {production.id}
                    </Button>
                  </p>
                  <p>{production.name}</p>
                  <p>{production.price}</p>
                  <p>{production.description}</p>
                  <p>{production.imageURL}</p>
                  <p>{production.salePrice}</p>
                  <p>{production.quantity}</p>
                  <p>{production.condition}</p>
                  <p>{production.origin}</p>
                  <p>{production.configuration}</p>
                  <p>
                    {production.creationDate ? (
                      <TextFormat type="date" value={production.creationDate} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </p>
                  <p>{production.brand ? <Link to={`brand/${production.brand.id}`}>{production.brand.id}</Link> : ''}</p>
                  <p>{production.category ? <Link to={`category/${production.category.id}`}>{production.category.id}</Link> : ''}</p>
                  <p className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${production.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                    </div>
                  </p>
                </div>
              ))}
            </>
          ) : (
            !loading && (
              <div className="alert alert-warning">
                <Translate contentKey="tinhocdanangApp.production.home.notFound">No Productions found</Translate>
              </div>
            )
          )}
        </div>
        <div>
          {props.totalItems ? (
            <div className={productionList && productionList.length > 0 ? null : 'd-none'}>
              <Row className="justify-content-center">
                <JhiItemCount
                  page={paginationState.activePage}
                  total={totalItems}
                  itemsPerPage={paginationState.itemsPerPage}
                  i18nEnabled
                />
              </Row>
              <Row className="justify-content-center">
                <JhiPagination
                  activePage={paginationState.activePage}
                  onSelect={handlePagination}
                  maxButtons={5}
                  itemsPerPage={paginationState.itemsPerPage}
                  totalItems={props.totalItems}
                />
              </Row>
            </div>
          ) : (
            ''
          )}
        </div>
      </div>
    </div>
  );
};

const mapStateToProps = ({ production }: IRootState) => ({
  productionList: production.entities,
  loading: production.loading,
  totalItems: production.totalItems,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(Home);
