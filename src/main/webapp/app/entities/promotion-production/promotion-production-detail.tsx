import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './promotion-production.reducer';
import { IPromotionProduction } from 'app/shared/model/promotion-production.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IPromotionProductionDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const PromotionProductionDetail = (props: IPromotionProductionDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { promotionProductionEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="tinhocdanangApp.promotionProduction.detail.title">PromotionProduction</Translate> [
          <b>{promotionProductionEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <Translate contentKey="tinhocdanangApp.promotionProduction.production">Production</Translate>
          </dt>
          <dd>{promotionProductionEntity.production ? promotionProductionEntity.production.id : ''}</dd>
          <dt>
            <Translate contentKey="tinhocdanangApp.promotionProduction.promotion">Promotion</Translate>
          </dt>
          <dd>{promotionProductionEntity.promotion ? promotionProductionEntity.promotion.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/promotion-production" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/promotion-production/${promotionProductionEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ promotionProduction }: IRootState) => ({
  promotionProductionEntity: promotionProduction.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(PromotionProductionDetail);
